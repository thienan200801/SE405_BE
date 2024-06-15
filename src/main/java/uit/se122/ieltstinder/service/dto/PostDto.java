package uit.se122.ieltstinder.service.dto;

import uit.se122.ieltstinder.entity.User;
import uit.se122.ieltstinder.entity.enumeration.PostStatus;

import java.time.Instant;

public record PostDto(
        String key,
        Long id,
        String name,
        String url,
        Instant createdDate,
        PostStatus status,
        Long duration
) {
}
