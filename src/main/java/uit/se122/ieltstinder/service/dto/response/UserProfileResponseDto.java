package uit.se122.ieltstinder.service.dto.response;

import uit.se122.ieltstinder.entity.enumeration.TestLevel;

public record UserProfileResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String address,
        String gender,
        Integer age,
        String description,
        String avatar,
        String token,
        TestLevel level
) {
}
