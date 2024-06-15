package uit.se122.ieltstinder.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
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
import uit.se122.ieltstinder.service.FriendService;
import uit.se122.ieltstinder.service.criteria.FriendCriteria;
import uit.se122.ieltstinder.service.dto.FriendDto;
import uit.se122.ieltstinder.util.PaginationUtils;

import java.util.List;

@RestController
@RequestMapping(value = "/api/friends")
@RequiredArgsConstructor
@Tag(name = "Friend Resources")
public class FriendController {

    private final FriendService friendService;

    @DeleteMapping(value = "/{friendId}")
    public ResponseEntity<Void> unfriend(@PathVariable Long friendId) {
        friendService.unfriend(SecurityUtils.getCurrentUserId(), friendId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FriendDto>> getFriends(FriendCriteria criteria, @ParameterObject @PageableDefault Pageable pageable) {
        final Page<FriendDto> page = friendService.getFriends(SecurityUtils.getCurrentUserId(), criteria, pageable);
        final HttpHeaders headers = PaginationUtils
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
