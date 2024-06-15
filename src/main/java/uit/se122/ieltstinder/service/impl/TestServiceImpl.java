package uit.se122.ieltstinder.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uit.se122.ieltstinder.entity.Test;
import uit.se122.ieltstinder.entity.Test_;
import uit.se122.ieltstinder.exception.BadRequestException;
import uit.se122.ieltstinder.repository.TestRepository;
import uit.se122.ieltstinder.service.ResourceService;
import uit.se122.ieltstinder.service.TestService;
import uit.se122.ieltstinder.service.criteria.TestCriteria;
import uit.se122.ieltstinder.service.dto.TestDetailDto;
import uit.se122.ieltstinder.service.dto.TestDto;
import uit.se122.ieltstinder.service.dto.request.CreateTestDto;
import uit.se122.ieltstinder.service.dto.request.TestRequest;
import uit.se122.ieltstinder.service.mapper.TestMapper;
import uit.se122.ieltstinder.service.query.QueryService;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

import static uit.se122.ieltstinder.constant.MessageConstant.TEST_NOT_EXIST;

@Service
@RequiredArgsConstructor
public class TestServiceImpl extends QueryService<Test> implements TestService {

    private final TestRepository testRepository;
    private final TestMapper testMapper;
    private final ResourceService resourceService;

    @Override
    public Page<TestDto> getAllTests(TestCriteria criteria, Pageable pageable) {
        Specification<Test> specification = createSpecification(criteria);
        return testRepository.findAll(specification, pageable).map(testMapper::toTestDto);
    }

    @Override
    public TestDetailDto getTest(Long testId) {
        Test test = testRepository
                .findById(testId)
                .orElseThrow(() -> new BadRequestException(TEST_NOT_EXIST));
        return testMapper.toTestDetailDto(test);
    }

    @Override
    @Transactional
    public void updateTest(Long testId, TestRequest request) {
        Test test = testRepository
                .findById(testId)
                .orElseThrow(() -> new BadRequestException(TEST_NOT_EXIST));

        test.setTitle(request.getTitle());
        test.setDifficultyLevel(request.getDifficultyLevel());
    }

    @Override
    @Transactional
    public void addReadingResource(Long testId, MultipartFile resource, String paragraph) {
        Test test = testRepository
                .findById(testId)
                .orElseThrow(() -> new BadRequestException(TEST_NOT_EXIST));

//        if (Objects.nonNull(resource)) {
//            String url = resourceService.uploadImage(resource);
//            test.setImage(url);
//        }
//
//        if (Objects.nonNull(paragraph)) {
//            test.setParagraph(paragraph);
//        }
    }

    @Override
    @Transactional
    public void createTest(CreateTestDto request) {
        testRepository.save(Test.builder()
                .title(request.getName())
                .difficultyLevel(request.getLevel())
                .createdAt(Instant.now())
                .build());
    }

    @Override
    public void deleteTest(Long testId) {
        Test test = testRepository
                .findById(testId)
                .orElseThrow(() -> new BadRequestException(TEST_NOT_EXIST));
        testRepository.delete(test);
    }

    @Override
    public TestDetailDto getEntryTest() {
        Test test = testRepository
                .findById(1L)
                .orElseThrow(() -> new BadRequestException(TEST_NOT_EXIST));
        return testMapper.toTestDetailDto(test);
    }

    @Override
    public Test getTestEntityById(Long testId) {
        return testRepository
                .findById(testId)
                .orElseThrow(() -> new BadRequestException(TEST_NOT_EXIST));
    }

    private Specification<Test> createSpecification(TestCriteria criteria) {
        Specification<Test> specification = Specification.where(null);

        if (criteria != null) {
            if (Objects.nonNull(criteria.getName())) {
                specification = specification.and(buildSpecification(criteria.getName(),
                        root -> root.get(Test_.title)));
            }

            if (Objects.nonNull(criteria.getLevel())) {
                specification = specification.and(buildSpecification(criteria.getLevel(),
                        root -> root.get(Test_.difficultyLevel)));
            }

            if (Objects.nonNull(criteria.getTestId())) {
                specification = specification.and(buildSpecification(criteria.getTestId(),
                        root -> root.get(Test_.id)));
            }
        }

        return  specification;
    }
}
