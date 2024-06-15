package uit.se122.ieltstinder.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uit.se122.ieltstinder.entity.Message;
import uit.se122.ieltstinder.socket.dto.MessageResponse;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    public MessageResponse toMessageResponse(Message message) {
        return new MessageResponse(
                message.getUser().getId(),
                message.getMessage(),
                message.getDate()
        );
    }

}
