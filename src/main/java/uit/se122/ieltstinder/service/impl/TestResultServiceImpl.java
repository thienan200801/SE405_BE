package uit.se122.ieltstinder.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uit.se122.ieltstinder.entity.Test;
import uit.se122.ieltstinder.entity.TestResult;
import uit.se122.ieltstinder.entity.User;
import uit.se122.ieltstinder.entity.enumeration.TestLevel;
import uit.se122.ieltstinder.exception.BadRequestException;
import uit.se122.ieltstinder.repository.TestRepository;
import uit.se122.ieltstinder.repository.TestResultRepository;
import uit.se122.ieltstinder.repository.UserRepository;
import uit.se122.ieltstinder.service.TestResultService;
import uit.se122.ieltstinder.service.dto.request.EntryTestSubmitRequest;
import uit.se122.ieltstinder.service.dto.request.SubmitTestRequest;
import uit.se122.ieltstinder.service.dto.response.EntryTestSubmitResponse;
import uit.se122.ieltstinder.service.query.QueryService;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static uit.se122.ieltstinder.constant.MessageConstant.TEST_NOT_EXIST;
import static uit.se122.ieltstinder.constant.MessageConstant.USER_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class TestResultServiceImpl extends QueryService<TestResult> implements TestResultService {

    private final TestResultRepository testResultRepository;
    private final UserRepository userRepository;
    private final TestRepository testRepository;

    @Override
    @Transactional
    public void submitResult(Long userId, SubmitTestRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));

        Test test = testRepository.findById(request.getTestId())
                        .orElseThrow(() -> new BadRequestException(TEST_NOT_EXIST));

        testResultRepository.save(TestResult.builder()
                .user(user)
                .test(test)
                        .result(89D)
                .time(request.getTime())
                .build());
    }

    @Override
    @Transactional
    public EntryTestSubmitResponse entryTestSubmit(Long userId, EntryTestSubmitRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));
        Test test = testRepository.findById(request.getTestId())
                .orElseThrow(() -> new BadRequestException(TEST_NOT_EXIST));

        AtomicInteger totalQuestions = new AtomicInteger(0);
        AtomicInteger correctQuestions = new AtomicInteger(0);
        AtomicInteger totalPoints = new AtomicInteger(0);

        test.getQuestions().forEach(question -> {
            question.getQuestionDetails().forEach(questionDetail -> {
                totalQuestions.addAndGet(1);
                questionDetail.getAnswers().forEach(answer -> {
                    request.getAnswerIds().forEach(requestAnswerId -> {
                        if (Objects.equals(requestAnswerId, answer.getId()) && answer.getIsCorrect()) {
                            correctQuestions.addAndGet(1);
                            totalPoints.addAndGet(questionDetail.getPoint());
                        }
                    });
                });
            });
        });

        TestLevel level = TestLevel.ENTRY_TEST;
        if (totalPoints.get() <= 5) {
            level = TestLevel.A1;
        } else if (totalPoints.get() > 5 && totalPoints.get() <= 15) {
            level = TestLevel.A2;
        } else if (totalPoints.get() > 15 && totalPoints.get() <= 30) {
            level = TestLevel.B1;
        } else if (totalPoints.get() > 30 && totalPoints.get() <= 50) {
            level = TestLevel.B2;
        } else if (totalPoints.get() > 50 && totalPoints.get() <= 75) {
            level = TestLevel.C1;
        } else {
            level = TestLevel.C2;
        }

        user.setLevel(level);

        return new EntryTestSubmitResponse(totalQuestions.get(), correctQuestions.get(), totalPoints.get());
    }
}
