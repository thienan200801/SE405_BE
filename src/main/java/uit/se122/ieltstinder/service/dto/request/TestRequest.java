package uit.se122.ieltstinder.service.dto.request;

import lombok.Data;
import uit.se122.ieltstinder.entity.enumeration.TestLevel;

@Data
public class TestRequest {
    private String title;
    private TestLevel difficultyLevel;
}
