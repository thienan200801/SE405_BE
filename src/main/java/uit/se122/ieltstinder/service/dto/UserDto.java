package uit.se122.ieltstinder.service.dto;

import uit.se122.ieltstinder.entity.enumeration.Role;
import uit.se122.ieltstinder.entity.enumeration.TestLevel;
import uit.se122.ieltstinder.entity.enumeration.UserStatus;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        String address,
        String gender,
        Integer age,
        String description,
        String avatar,
        UserStatus status,
        Role role,
        TestLevel level
) {
}
