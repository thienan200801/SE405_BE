package uit.se122.ieltstinder.socket.dto;

import uit.se122.ieltstinder.socket.enumeration.MessageType;

public record RequestCallResponse(
        Long chatroomId,
        String name,
        String avatar,
        Long userId,
        MessageType type,
        String roomId
) {
}
