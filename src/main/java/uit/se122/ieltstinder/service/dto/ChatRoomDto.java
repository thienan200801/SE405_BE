package uit.se122.ieltstinder.service.dto;

import java.time.Instant;

public record ChatRoomDto(
        Long id,
        UserDto user
) {
}
