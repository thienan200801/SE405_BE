package uit.se122.ieltstinder.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uit.se122.ieltstinder.entity.ChatRoom;
import uit.se122.ieltstinder.entity.ChatRoom_;
import uit.se122.ieltstinder.entity.User_;
import uit.se122.ieltstinder.repository.ChatRoomRepository;
import uit.se122.ieltstinder.service.ChatRoomService;
import uit.se122.ieltstinder.service.criteria.ChatRoomCriteria;
import uit.se122.ieltstinder.service.dto.ChatRoomDto;
import uit.se122.ieltstinder.service.mapper.ChatRoomMapper;
import uit.se122.ieltstinder.service.query.QueryService;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl extends QueryService<ChatRoom> implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMapper chatRoomMapper;

    @Override
    public List<ChatRoomDto> getChatRooms(ChatRoomCriteria criteria) {
        Specification<ChatRoom> specification = createSpecification(criteria);
        return chatRoomRepository.findAll(specification).stream().map(chatRoomMapper::toChatRoomDto).toList();
    }

    private Specification<ChatRoom> createSpecification(ChatRoomCriteria criteria) {
        Specification<ChatRoom> specification = Specification.where(null);

        if (criteria != null) {
            if (Objects.nonNull(criteria.getUserId())) {
                Specification<ChatRoom> specificationCustom = buildSpecification(criteria.getUserId(),
                        root -> root.get(ChatRoom_.user1).get(User_.id));
                specificationCustom = specificationCustom.or(buildSpecification(criteria.getUserId(),
                        root -> root.get(ChatRoom_.user2).get(User_.id)));
                specification = specification.and(specificationCustom);
            }
        }

        return specification;
    }
}
