package uit.se122.ieltstinder.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import uit.se122.ieltstinder.entity.ChatRoom;
import uit.se122.ieltstinder.entity.Message;
import uit.se122.ieltstinder.entity.User;
import uit.se122.ieltstinder.exception.BadRequestException;
import uit.se122.ieltstinder.repository.ChatRoomRepository;
import uit.se122.ieltstinder.repository.MessageRepository;
import uit.se122.ieltstinder.repository.UserRepository;
import uit.se122.ieltstinder.service.ChatService;
import uit.se122.ieltstinder.socket.dto.MessageRequest;
import uit.se122.ieltstinder.socket.dto.MessageResponse;
import uit.se122.ieltstinder.socket.dto.RequestCallResponse;
import uit.se122.ieltstinder.socket.enumeration.MessageType;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static uit.se122.ieltstinder.constant.MessageConstant.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatServiceImpl implements ChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public MessageResponse sendMessage(MessageRequest request) {
        MessageResponse messageResponse = new MessageResponse(
                request.getUserId(),
                request.getMessage(),
                Instant.now()
        );

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));
        ChatRoom chatRoom = chatRoomRepository.findById(Long.parseLong(request.getChatroomId()))
                        .orElseThrow(() -> new BadRequestException(CHAT_ROOM_NOT_EXIST));

        if (request.getType() == MessageType.CALL || request.getType() == MessageType.CANCEL || request.getType() == MessageType.REJECT) {
            RequestCallResponse requestCallResponse = new RequestCallResponse(
                    chatRoom.getId(),
                    user.getFirstName() + " " + user.getLastName(),
                    user.getAvatar(),
                    user.getId(),
                    request.getType(),
                    ""
            );

            if (chatRoom.getUser1().getId().equals(request.getUserId())) {
                simpMessagingTemplate.convertAndSendToUser(chatRoom.getUser2().getId().toString(),
                        "notification", requestCallResponse);
            }
            else {
                simpMessagingTemplate.convertAndSendToUser(chatRoom.getUser1().getId().toString(),
                        "notification", requestCallResponse);
            }
            return messageResponse;
        } else if (request.getType() == MessageType.ACCEPT) {
            User user1 = userRepository.findById(chatRoom.getUser1().getId())
                    .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));

            User user2 = userRepository.findById(chatRoom.getUser2().getId())
                    .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));

            HttpClient httpClient = HttpClient
                    .newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest httpRequest = HttpRequest
                    .newBuilder()
                    .POST(BodyPublishers.ofString(""))
                    .uri(URI.create("https://api.videosdk.live/v2/rooms"))
                    .setHeader("Authorization", user.getVideoSdkToken()) // add request header
                    .build();

            try {
                HttpResponse<String> response = httpClient.send(
                        httpRequest,
                        HttpResponse.BodyHandlers.ofString()
                );

                String responseBody = response.body();

                // Parse JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                // Get roomId from the parsed JSON
                String roomId = jsonNode.get("roomId").asText();

                RequestCallResponse requestCallResponse = new RequestCallResponse(
                        chatRoom.getId(),
                        user1.getFirstName() + " " + user1.getLastName(),
                        user1.getAvatar(),
                        user1.getId(),
                        MessageType.ACCEPT,
                        roomId
                );

                RequestCallResponse requestCallResponse2 = new RequestCallResponse(
                        chatRoom.getId(),
                        user2.getFirstName() + " " + user2.getLastName(),
                        user2.getAvatar(),
                        user2.getId(),
                        MessageType.ACCEPT,
                        roomId
                );

                simpMessagingTemplate.convertAndSendToUser(chatRoom.getUser1().getId().toString(),
                        "notification", requestCallResponse2);

                simpMessagingTemplate.convertAndSendToUser(chatRoom.getUser2().getId().toString(),
                        "notification", requestCallResponse);

                return messageResponse;
            } catch (IOException | InterruptedException e) {
                throw new BadRequestException(START_CALL_ERROR);
            }
        }

        messageRepository.save(Message
                .builder()
                .user(user)
                .message(request.getMessage())
                .date(Instant.now())
                .chatRoom(chatRoom)
                .build()
        );

        simpMessagingTemplate.convertAndSendToUser(request.getChatroomId(), "private", messageResponse);
        return messageResponse;
    }

}
