package uit.se122.ieltstinder.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.se122.ieltstinder.entity.*;
import uit.se122.ieltstinder.exception.BadRequestException;
import uit.se122.ieltstinder.repository.ChatRoomRepository;
import uit.se122.ieltstinder.repository.FriendRepository;
import uit.se122.ieltstinder.repository.RequestRepository;
import uit.se122.ieltstinder.repository.UserRepository;
import uit.se122.ieltstinder.service.RequestService;
import uit.se122.ieltstinder.service.criteria.RequestCriteria;
import uit.se122.ieltstinder.service.dto.RequestDto;
import uit.se122.ieltstinder.service.mapper.RequestMapper;
import uit.se122.ieltstinder.service.query.QueryService;

import java.util.Objects;

import static uit.se122.ieltstinder.constant.MessageConstant.*;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl extends QueryService<Request> implements RequestService {

    private final FriendRepository friendRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final RequestMapper requestMapper;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    @Transactional
    public void createRequest(Long userId, Long targetUserId) {
        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));
        User targetUser = userRepository.findById(targetUserId)
                        .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));

        requestRepository.save(Request
                .builder()
                .sender(user)
                .receiver(targetUser)
                .build()
        );
    }

    @Override
    public Page<RequestDto> getRequests(RequestCriteria criteria, Pageable pageable) {
        Specification<Request> specification = createSpecification(criteria);
        return requestRepository.findAll(specification, pageable).map(requestMapper::toRequestDto);
    }

    @Override
    public Page<RequestDto> getSentRequest(Long userId, Pageable pageable) {
        return requestRepository.findByUserId(userId, pageable).map(requestMapper::toRequestDto);
    }

    @Override
    public Page<RequestDto> getReceivedRequest(Long userId, Pageable pageable) {
        return requestRepository.findRequestReceived(userId, pageable).map(requestMapper::toRequestDto);
    }

    private Specification<Request> createSpecification(RequestCriteria criteria) {
        Specification<Request> specification = Specification.where(null);
        if (criteria != null) {
            if (Objects.nonNull(criteria.getReceiver())) {
                specification = specification.and(buildSpecification(criteria.getReceiver(),
                        root -> root.get(Request_.receiver).get(User_.id)));
            }
            if (Objects.nonNull(criteria.getSender())) {
                specification = specification.and(buildSpecification(criteria.getSender(),
                        root -> root.get(Request_.sender).get(User_.id)));
            }
        }
        return  specification;
    }

    @Override
    public void deleteRequest(Long requestId) {
        requestRepository.deleteById(requestId);
    }

    @Transactional
    @Override
    public void acceptRequest(Long sender, Long receiver) {
        Request request = requestRepository.findRequestBySenderAndReceiver(sender, receiver)
                        .orElseThrow(() -> new BadRequestException(REQUEST_NOT_EXIST));
        User user = userRepository.findById(request.getSender().getId())
                .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));
        User targetUser = userRepository.findById(request.getReceiver().getId())
                .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));

        friendRepository.save(Friend
                .builder()
                .user(user)
                .friend(targetUser)
                .build()
        );

        chatRoomRepository.save(ChatRoom
                .builder()
                .user1(user)
                .user2(targetUser)
                .build()
        );

        requestRepository.deleteBySenderAndReceiver(sender, receiver);
    }

    @Override
    @Transactional
    public void deleteRequestByUserId(Long sender, Long receiver) {
        requestRepository.findRequestBySenderAndReceiver(sender, receiver)
                .orElseThrow(() -> new BadRequestException(REQUEST_ACCEPTED));

        requestRepository.deleteBySenderAndReceiver(sender, receiver);
    }

}
