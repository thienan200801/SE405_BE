package uit.se122.ieltstinder.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import uit.se122.ieltstinder.exception.ResourceNotFoundException;

import java.util.Optional;

public final class ResponseUtils {

    private ResponseUtils() {}

    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return maybeResponse.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(ResourceNotFoundException::new);
    }

    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return maybeResponse.map(response -> ResponseEntity.ok().headers(header).body(response))
                .orElseThrow(ResourceNotFoundException::new);
    }
}
