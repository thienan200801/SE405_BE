package uit.se122.ieltstinder.service.dto.response;

import uit.se122.ieltstinder.entity.enumeration.TestLevel;
import uit.se122.ieltstinder.entity.enumeration.UserStatus;
import uit.se122.ieltstinder.service.dto.PostDto;

import java.util.List;

public record AdminUserDetailResponse(
        String key,
        Long id,
        String firstName,
        String lastName,
        String email,
        String address,
        String gender,
        Integer age,
        String description,
        String avatar,
        UserStatus status,
        TestLevel level,
        List<PostDto> posts
) {
}
