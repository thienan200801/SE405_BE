package uit.se122.ieltstinder.service.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SubmitTestRequest {
    private Long testId;
    private List<Long> answerId;
    private Long time;
}
