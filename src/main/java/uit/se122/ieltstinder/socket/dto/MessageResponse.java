package uit.se122.ieltstinder.socket.dto;

import java.time.Instant;

public record MessageResponse (
        Long userId,
        String message,
        Instant date
) {
}
