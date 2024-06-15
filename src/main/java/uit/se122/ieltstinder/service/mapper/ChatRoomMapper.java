package uit.se122.ieltstinder.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uit.se122.ieltstinder.entity.ChatRoom;
import uit.se122.ieltstinder.security.SecurityUtils;
import uit.se122.ieltstinder.service.dto.ChatRoomDto;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ChatRoomMapper {

    private final UserMapper userMapper;

    public ChatRoomDto toChatRoomDto(ChatRoom chatRoom) {
        return new ChatRoomDto(
                chatRoom.getId(),
                userMapper.toUserDto(Objects.equals(chatRoom.getUser1().getId(), SecurityUtils.getCurrentUserId()) ? chatRoom.getUser2() : chatRoom.getUser1())
        );
    }

}
