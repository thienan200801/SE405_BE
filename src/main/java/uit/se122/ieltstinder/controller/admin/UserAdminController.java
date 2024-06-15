package uit.se122.ieltstinder.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
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
import uit.se122.ieltstinder.service.ResourceService;
import uit.se122.ieltstinder.service.UserService;
import uit.se122.ieltstinder.service.criteria.UserCriteria;
import uit.se122.ieltstinder.service.dto.UserAdminDto;
import uit.se122.ieltstinder.service.dto.response.AdminUserDetailResponse;
import uit.se122.ieltstinder.util.PaginationUtils;

import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/users")
@RequiredArgsConstructor
@Tag(name = "Admin User Resource")
public class UserAdminController {

    private final UserService userService;
    private final ResourceService resourceService;

    @GetMapping
    public ResponseEntity<List<UserAdminDto>> getUsers(UserCriteria criteria, @ParameterObject @PageableDefault Pageable pageable) {
        final Page<UserAdminDto> page = userService.getAllUsers(criteria, pageable);
        final HttpHeaders headers = PaginationUtils
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping(value = "/{userId}/block")
    public ResponseEntity<Void> blockUser(@PathVariable Long userId) {
        userService.blockUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{userId}/unblock")
    public ResponseEntity<Void> unblockUser(@PathVariable Long userId) {
        userService.unblockUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/add", consumes = { "multipart/form-data" })
    public ResponseEntity<String> addSocialMedia(@RequestPart("video") MultipartFile audio) {
            return ResponseEntity.ok(resourceService.uploadVideo(audio));
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<AdminUserDetailResponse> getUserDetail(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getAdminUserDetail(userId));
    }

}
