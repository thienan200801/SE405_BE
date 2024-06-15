package uit.se122.ieltstinder.service;

import uit.se122.ieltstinder.service.dto.request.EntryTestSubmitRequest;
import uit.se122.ieltstinder.service.dto.request.SubmitTestRequest;
import uit.se122.ieltstinder.service.dto.response.EntryTestSubmitResponse;

public interface TestResultService {
    void submitResult(Long userId, SubmitTestRequest request);
    EntryTestSubmitResponse entryTestSubmit(Long userId, EntryTestSubmitRequest request);
}
