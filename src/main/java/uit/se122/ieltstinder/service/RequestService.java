package uit.se122.ieltstinder.service;

import org.springframework.data.domain.Page;
import uit.se122.ieltstinder.service.criteria.RequestCriteria;
import uit.se122.ieltstinder.service.dto.RequestDto;
import org.springframework.data.domain.Pageable;

public interface RequestService {

    void createRequest(Long userId, Long targetUserId);
    Page<RequestDto> getRequests(RequestCriteria criteria, Pageable pageable);
    Page<RequestDto> getSentRequest(Long userId, Pageable pageable);
    Page<RequestDto> getReceivedRequest(Long userId, Pageable pageable);
    void deleteRequest(Long requestId);
    void acceptRequest(Long sender, Long receiver);
    void deleteRequestByUserId(Long sender, Long receiver);

}
