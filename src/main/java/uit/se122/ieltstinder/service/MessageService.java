package uit.se122.ieltstinder.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uit.se122.ieltstinder.socket.dto.MessageResponse;

public interface MessageService {

    Page<MessageResponse> getMessages(Long chatroomId, Pageable pageable);

}
