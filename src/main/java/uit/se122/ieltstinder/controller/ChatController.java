package uit.se122.ieltstinder.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uit.se122.ieltstinder.service.ChatRoomService;
import uit.se122.ieltstinder.service.ChatService;
import uit.se122.ieltstinder.service.MessageService;
import uit.se122.ieltstinder.service.criteria.ChatRoomCriteria;
import uit.se122.ieltstinder.service.dto.ChatRoomDto;
import uit.se122.ieltstinder.socket.dto.MessageRequest;
import uit.se122.ieltstinder.socket.dto.MessageResponse;
import uit.se122.ieltstinder.util.PaginationUtils;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Chat Resource")
public class ChatController {

    private final ChatService chatService;
    private final ChatRoomService chatRoomService;
    private final MessageService messageService;

//    @MessageMapping("/message")
//    @SendTo("/queue/reply")
//    public MessageResponse receiveMessage(@Payload MessageRequest request) {
//        return chatService.sendMessage(request);
//    }

    @MessageMapping("/private-message")
    public MessageResponse receivePrivateMessage(@Payload MessageRequest request) {
        return chatService.sendMessage(request);
    }

    @GetMapping(value = "/chatrooms")
    public ResponseEntity<List<ChatRoomDto>> getChatRooms(ChatRoomCriteria criteria) {
        return ResponseEntity.ok(chatRoomService.getChatRooms(criteria));
    }

    @GetMapping(value = "/messages/{chatroomId}")
    public ResponseEntity<List<MessageResponse>> getMessages(@PathVariable Long chatroomId, @ParameterObject @PageableDefault Pageable pageable) {
        final Page<MessageResponse> page = messageService.getMessages(chatroomId, pageable);
        final HttpHeaders headers = PaginationUtils
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
