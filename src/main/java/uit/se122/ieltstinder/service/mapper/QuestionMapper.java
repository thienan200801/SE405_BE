package uit.se122.ieltstinder.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uit.se122.ieltstinder.entity.Question;
import uit.se122.ieltstinder.entity.QuestionDetail;
import uit.se122.ieltstinder.service.dto.QuestionDetailDto;
import uit.se122.ieltstinder.service.dto.QuestionDto;

@Component
@RequiredArgsConstructor
public class QuestionMapper {

    private final AnswerMapper answerMapper;

    public QuestionDetailDto toQuestionDetailDto(QuestionDetail questionDetail) {
        return new QuestionDetailDto(
                questionDetail.getId(),
                questionDetail.getText(),
                questionDetail.getExplain(),
                questionDetail.getAnswers().stream().map(answerMapper::toAnswerDto).toList()
        );
    }

    public QuestionDto toQuestionDto(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getParagraph(),
                question.getAudioUrl(),
                question.getType(),
                question.getDescription(),
                question.getQuestionDetails().stream().map(this::toQuestionDetailDto).toList()
        );
    }

}
