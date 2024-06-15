package uit.se122.ieltstinder.service;

import uit.se122.ieltstinder.socket.dto.MessageRequest;
import uit.se122.ieltstinder.socket.dto.MessageResponse;

public interface ChatService {

    MessageResponse sendMessage(MessageRequest request);

}
