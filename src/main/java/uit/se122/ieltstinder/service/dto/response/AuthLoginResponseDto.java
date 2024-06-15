package uit.se122.ieltstinder.service.dto.response;

public record AuthLoginResponseDto(
        Long id,
        String accessToken,
        String refreshToken
) {
}
