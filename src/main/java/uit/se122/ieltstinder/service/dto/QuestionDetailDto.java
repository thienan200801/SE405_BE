package uit.se122.ieltstinder.service.dto;

import java.util.List;

public record QuestionDetailDto(
        Long id,
        String text,
        String explain,
        List<AnswerDto> answers
) {
}
