package uit.se122.ieltstinder.service.dto.request;

import lombok.Data;
import uit.se122.ieltstinder.entity.enumeration.PartType;

import java.util.List;

@Data
public class QuestionReadingRequestDto {
    private Long testId;
    private PartType type;
    private String question;
    private List<AnswerRequestDto> answers;
}
