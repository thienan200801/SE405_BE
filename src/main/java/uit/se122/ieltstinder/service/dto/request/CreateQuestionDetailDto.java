package uit.se122.ieltstinder.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

import static uit.se122.ieltstinder.constant.MessageConstant.ANSWER_LIST_REQUIRED_ERROR;
import static uit.se122.ieltstinder.constant.MessageConstant.QUESTION_CONTENT_REQUIRED_ERROR;

@Data
public class CreateQuestionDetailDto {

    @NotBlank(message = QUESTION_CONTENT_REQUIRED_ERROR)
    private String content;

    private String explain;
    private Integer point;

    @NotNull(message = ANSWER_LIST_REQUIRED_ERROR)
    List<CreateAnswerDto> answers;

}
