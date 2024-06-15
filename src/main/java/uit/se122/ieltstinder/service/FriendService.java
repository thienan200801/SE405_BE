package uit.se122.ieltstinder.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uit.se122.ieltstinder.service.criteria.FriendCriteria;
import uit.se122.ieltstinder.service.dto.FriendDto;

public interface FriendService {

    void unfriend(Long userId, Long friendId);
    Page<FriendDto> getFriends(Long friendId, FriendCriteria criteria, Pageable pageable);

}
