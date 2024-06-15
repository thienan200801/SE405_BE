package uit.se122.ieltstinder.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import uit.se122.ieltstinder.entity.enumeration.QuestionType;

import java.util.List;

import static uit.se122.ieltstinder.constant.MessageConstant.*;

@Data
public class CreateQuestionDto {

    // Question properties
    @NotNull(message = TEST_ID_REQUIRED_ERROR)
    private Long testId;

    @NotNull(message = QUESTION_TYPE_REQUIRED_ERROR)
    private QuestionType questionType;

    private String description;
    private String paragraph;
    private MultipartFile audio;

    // Question detail properties
    @NotNull(message = LIST_QUESTION_DETAILS_REQUIRED_ERROR)
    private List<CreateQuestionDetailDto> questionDetails;

}
