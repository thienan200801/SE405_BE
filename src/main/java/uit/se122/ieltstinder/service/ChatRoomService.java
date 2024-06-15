package uit.se122.ieltstinder.service;

import org.springframework.data.domain.Page;
import uit.se122.ieltstinder.service.criteria.ChatRoomCriteria;
import uit.se122.ieltstinder.service.dto.ChatRoomDto;

import java.util.List;

public interface ChatRoomService {
    List<ChatRoomDto> getChatRooms(ChatRoomCriteria criteria);
}
