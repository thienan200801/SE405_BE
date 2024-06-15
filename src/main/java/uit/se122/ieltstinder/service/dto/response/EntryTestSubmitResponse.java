package uit.se122.ieltstinder.service.dto.response;

public record EntryTestSubmitResponse(
        Integer totalQuestions,
        Integer correctQuestions,
        Integer totalPoints
) {
}
