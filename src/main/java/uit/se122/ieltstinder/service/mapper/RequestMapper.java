package uit.se122.ieltstinder.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uit.se122.ieltstinder.entity.Request;
import uit.se122.ieltstinder.service.dto.RequestDto;

@Component
@RequiredArgsConstructor
public class RequestMapper {

    private final UserMapper userMapper;

    public RequestDto toRequestDto(Request request) {
        return new RequestDto(
                request.getId(),
                userMapper.toUserDto(request.getSender()),
                userMapper.toUserDto(request.getReceiver())
        );
    }

}
