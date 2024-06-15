package uit.se122.ieltstinder.service.dto;

import uit.se122.ieltstinder.entity.enumeration.QuestionType;

import java.util.List;

public record QuestionDto(
        Long id,
        String paragraph,
        String audioUrl,
        QuestionType type,
        String description,
        List<QuestionDetailDto> questionDetails
) {
}
