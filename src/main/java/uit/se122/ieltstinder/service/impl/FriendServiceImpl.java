package uit.se122.ieltstinder.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.se122.ieltstinder.entity.Friend;
import uit.se122.ieltstinder.entity.Friend_;
import uit.se122.ieltstinder.entity.User_;
import uit.se122.ieltstinder.repository.ChatRoomRepository;
import uit.se122.ieltstinder.repository.FriendRepository;
import uit.se122.ieltstinder.service.FriendService;
import uit.se122.ieltstinder.service.criteria.FriendCriteria;
import uit.se122.ieltstinder.service.dto.FriendDto;
import uit.se122.ieltstinder.service.mapper.FriendMapper;
import uit.se122.ieltstinder.service.query.QueryService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class FriendServiceImpl extends QueryService<Friend> implements FriendService {

    private final FriendRepository friendRepository;
    private final FriendMapper friendMapper;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    @Transactional
    public void unfriend(Long userId, Long friendId) {
        friendRepository.deleteByUserIdAndFriendId(userId, friendId);
        chatRoomRepository.deleteByTwoUserId(userId, friendId);
    }

    @Override
    public Page<FriendDto> getFriends(Long friendId, FriendCriteria criteria, Pageable pageable) {
        Specification<Friend> specification = createSpecification(criteria);
        return friendRepository.findByFkUserId(friendId, pageable).map(friendMapper::toFriendDto);
    }

    private Specification<Friend> createSpecification(FriendCriteria criteria) {
        Specification<Friend> specification = Specification.where(null);
        if (criteria != null) {
            if (Objects.nonNull(criteria.getName())) {
                Specification<Friend> specificationCustom = buildSpecification(criteria.getName(),
                        root -> root.get(Friend_.friend).get(User_.firstName));
                specificationCustom = specificationCustom.or(buildSpecification(criteria.getName(),
                        root -> root.get(Friend_.friend).get(User_.lastName)));

                specification = specification.and(specificationCustom);
            }
        }

        return specification;
    }

}
