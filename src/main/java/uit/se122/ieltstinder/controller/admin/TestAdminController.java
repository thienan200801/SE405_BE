package uit.se122.ieltstinder.controller.admin;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uit.se122.ieltstinder.service.TestService;
import uit.se122.ieltstinder.service.criteria.TestCriteria;
import uit.se122.ieltstinder.service.dto.TestDetailDto;
import uit.se122.ieltstinder.service.dto.TestDto;
import uit.se122.ieltstinder.service.dto.request.CreateTestDto;
import uit.se122.ieltstinder.service.dto.request.TestRequest;
import uit.se122.ieltstinder.util.PaginationUtils;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/tests")
@RequiredArgsConstructor
@Tag(name = "Admin Test Resource")
public class TestAdminController {

    private final TestService testService;

    @GetMapping
    public ResponseEntity<List<TestDto>> getAllTests(TestCriteria criteria, @ParameterObject @PageableDefault Pageable pageable) {
        final Page<TestDto> page = testService.getAllTests(criteria, pageable);
        final HttpHeaders headers = PaginationUtils
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping(value = "/{testId}")
    public ResponseEntity<TestDetailDto> getTest(@PathVariable Long testId) {
        return ResponseEntity.ok(testService.getTest(testId));
    }

    @PostMapping(value = "/{testId}")
    public ResponseEntity<Void> updateTest(@PathVariable Long testId, @RequestBody TestRequest request) {
        testService.updateTest(testId, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{testId}/reading/add", consumes = { "multipart/form-data" })
    public ResponseEntity<Void> addReadingResource(@PathVariable Long testId,
                                                   @RequestPart(name = "resource", required = false) MultipartFile resource,
                                                   @RequestPart(name = "paragraph", required = false) String paragraph) {
        testService.addReadingResource(testId, resource, paragraph);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Void> createNewTest(@Valid @RequestBody CreateTestDto request) {
        testService.createTest(request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{testId}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long testId) {
        testService.deleteTest(testId);
        return ResponseEntity.noContent().build();
    }

}
