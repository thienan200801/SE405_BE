package uit.se122.ieltstinder.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uit.se122.ieltstinder.entity.Friend;
import uit.se122.ieltstinder.service.dto.FriendDto;

@Component
@RequiredArgsConstructor
public class FriendMapper {

    private final UserMapper userMapper;

    public FriendDto toFriendDto(Friend friend) {
        return new FriendDto(
                friend.getId(),
                userMapper.toUserDto(friend.getUser()),
                userMapper.toUserDto(friend.getFriend())
        );
    }

}
