package uit.se122.ieltstinder.service.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class EntryTestSubmitRequest {
    private Long testId;
    private List<Long> answerIds;
}
