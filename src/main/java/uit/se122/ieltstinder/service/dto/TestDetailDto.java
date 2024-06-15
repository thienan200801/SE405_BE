package uit.se122.ieltstinder.service.dto;

import uit.se122.ieltstinder.entity.enumeration.TestLevel;

import java.time.Instant;
import java.util.List;

public record TestDetailDto (
        Long id,
        String title,
        TestLevel difficultyLevel,
        Instant createdAt,
        List<QuestionDto> questions
) {
}
