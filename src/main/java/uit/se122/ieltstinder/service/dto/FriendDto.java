package uit.se122.ieltstinder.service.dto;

public record FriendDto(
        Long id,
        UserDto user,
        UserDto friend
) {
}
