package uit.se122.ieltstinder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uit.se122.ieltstinder.entity.TestResult;
import uit.se122.ieltstinder.service.dto.request.SubmitTestRequest;

public interface TestResultRepository extends JpaRepository<TestResult, Long> {

}
