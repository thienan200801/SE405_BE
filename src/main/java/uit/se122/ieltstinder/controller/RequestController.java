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
import uit.se122.ieltstinder.entity.Request;
import uit.se122.ieltstinder.security.SecurityUtils;
import uit.se122.ieltstinder.service.RequestService;
import uit.se122.ieltstinder.service.criteria.RequestCriteria;
import uit.se122.ieltstinder.service.dto.RequestDto;
import uit.se122.ieltstinder.service.dto.request.CreateRequestDto;
import uit.se122.ieltstinder.util.PaginationUtils;

import java.util.List;

@RestController
@RequestMapping(value = "/api/requests")
@RequiredArgsConstructor
@Tag(name = "Request Resources")
public class RequestController {

    private final RequestService requestService;

    @PostMapping
    public ResponseEntity<Void> sendRequest(@Valid @RequestBody CreateRequestDto request) {
        requestService.createRequest(SecurityUtils.getCurrentUserId(), request.getTargetUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<RequestDto>> getRequests(RequestCriteria criteria, @ParameterObject @PageableDefault Pageable pageable) {
        final Page<RequestDto> page = requestService.getRequests(criteria, pageable);
        final HttpHeaders headers = PaginationUtils
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @DeleteMapping(value = "/{requestId}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long requestId) {
        requestService.deleteRequest(requestId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/user/{receiver}")
    public ResponseEntity<Void> deleteRequestByUserId(@PathVariable Long receiver) {
        requestService.deleteRequestByUserId(SecurityUtils.getCurrentUserId(), receiver);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/sent")
    private ResponseEntity<List<RequestDto>> getSentRequests(@ParameterObject @PageableDefault Pageable pageable) {
        final  Page<RequestDto> page = requestService.getSentRequest(SecurityUtils.getCurrentUserId(), pageable);
        final HttpHeaders headers = PaginationUtils
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping(value = "/received")
    private ResponseEntity<List<RequestDto>> getReceivedRequests(@ParameterObject @PageableDefault Pageable pageable) {
        final  Page<RequestDto> page = requestService.getReceivedRequest(SecurityUtils.getCurrentUserId(), pageable);
        final HttpHeaders headers = PaginationUtils
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping(value = "/accept/{userId}")
    public ResponseEntity<Void> acceptRequest(@PathVariable Long userId) {
        requestService.acceptRequest(userId, SecurityUtils.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/reject/{userId}")
    public ResponseEntity<Void> rejectRequest(@PathVariable Long userId) {
        requestService.deleteRequestByUserId(userId, SecurityUtils.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

}
