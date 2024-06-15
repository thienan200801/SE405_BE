package uit.se122.ieltstinder.service.dto;

public record AnswerDto(
        Long id,
        String content,
        Boolean isCorrect
) {
}
