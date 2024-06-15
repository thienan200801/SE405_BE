package uit.se122.ieltstinder.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import static uit.se122.ieltstinder.constant.MessageConstant.*;

@Data
public class CreateAnswerDto {

    @NotBlank(message = ANSWER_CONTENT_REQUIRED_ERROR)
    private String content;

    @NotNull(message = ANSWER_CORRECT_REQUIRED_ERROR)
    private boolean isCorrect;

}
