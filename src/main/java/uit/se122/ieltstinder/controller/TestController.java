package uit.se122.ieltstinder.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uit.se122.ieltstinder.security.SecurityUtils;
import uit.se122.ieltstinder.service.TestResultService;
import uit.se122.ieltstinder.service.TestService;
import uit.se122.ieltstinder.service.criteria.TestCriteria;
import uit.se122.ieltstinder.service.dto.TestDetailDto;
import uit.se122.ieltstinder.service.dto.TestDto;
import uit.se122.ieltstinder.service.dto.request.EntryTestSubmitRequest;
import uit.se122.ieltstinder.service.dto.request.SubmitTestRequest;
import uit.se122.ieltstinder.service.dto.response.EntryTestSubmitResponse;
import uit.se122.ieltstinder.util.PaginationUtils;

import java.util.List;

@RequestMapping(value = "/api/tests")
@RestController
@RequiredArgsConstructor
@Tag(name = "Test Resources")
public class TestController {

    private final TestService testService;
    private final TestResultService testResultService;

    @GetMapping
    public ResponseEntity<List<TestDto>> getTests(TestCriteria criteria, @ParameterObject @PageableDefault Pageable pageable) {
        Page<TestDto> page = testService.getAllTests(criteria, pageable);
        final HttpHeaders headers = PaginationUtils
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping(value = "/{testId}")
    public ResponseEntity<TestDetailDto> getTest(@PathVariable Long testId) {
        return ResponseEntity.ok(testService.getTest(testId));
    }

    @PostMapping(value = "/submit")
    public ResponseEntity<Void> submitResult(@RequestBody SubmitTestRequest request) {
        testResultService.submitResult(SecurityUtils.getCurrentUserId(), request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/entry")
    public ResponseEntity<TestDetailDto> getEntryTest() {
        return ResponseEntity.ok(testService.getEntryTest());
    }

    @PostMapping(value = "/entry/submit")
    public ResponseEntity<EntryTestSubmitResponse> submitEntryTest(@Valid @RequestBody EntryTestSubmitRequest request) {
        return ResponseEntity.ok(testResultService.entryTestSubmit(SecurityUtils.getCurrentUserId(), request));
    }

}
