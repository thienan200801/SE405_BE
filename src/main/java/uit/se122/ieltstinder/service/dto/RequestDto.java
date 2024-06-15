package uit.se122.ieltstinder.service.dto;

public record RequestDto (
        Long id,
        UserDto user,
        UserDto targetUser
) {
}
