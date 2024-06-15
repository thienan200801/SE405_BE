package uit.se122.ieltstinder.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uit.se122.ieltstinder.entity.Test;
import uit.se122.ieltstinder.service.dto.TestDetailDto;
import uit.se122.ieltstinder.service.dto.TestDto;

@Component
@RequiredArgsConstructor
public class TestMapper {

    private final QuestionMapper questionMapper;

    public TestDto toTestDto(Test test) {
        return new TestDto(
                "Test " + test.getId(),
                test.getId(),
                test.getTitle(),
                test.getDifficultyLevel(),
                test.getCreatedAt()
        );
    }

    public TestDetailDto toTestDetailDto(Test test) {
        return new TestDetailDto(
                test.getId(),
                test.getTitle(),
                test.getDifficultyLevel(),
                test.getCreatedAt(),
                test.getQuestions().stream().map(questionMapper::toQuestionDto).toList()
        );
    }

}
