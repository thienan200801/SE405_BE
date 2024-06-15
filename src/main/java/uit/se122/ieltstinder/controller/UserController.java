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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uit.se122.ieltstinder.security.SecurityUtils;
import uit.se122.ieltstinder.service.UserService;
import uit.se122.ieltstinder.service.dto.UserDto;
import uit.se122.ieltstinder.service.dto.request.UpdateUserProfile;
import uit.se122.ieltstinder.service.dto.response.UserProfileResponseDto;
import uit.se122.ieltstinder.util.PaginationUtils;

import java.util.List;

@RequestMapping(value = "/api/users")
@RestController
@RequiredArgsConstructor
@Tag(name = "User Resources")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUserForRequest(@ParameterObject @PageableDefault Pageable pageable) {
        final Page<UserDto> page = userService.getUserForRequest(SecurityUtils.getCurrentUserId(), pageable);
        final HttpHeaders headers = PaginationUtils
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok().body(userService.findUserById(userId));
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<UserProfileResponseDto> getProfile() {
        return ResponseEntity.ok(userService.getUserProfile(SecurityUtils.getCurrentUserId()));
    }

    @PostMapping(value = "/avatar", consumes = { "multipart/form-data" })
    public ResponseEntity<Void> updateAvatar(@RequestPart("image") MultipartFile image) {
        userService.updateAvatar(SecurityUtils.getCurrentUserId(), image);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Void> updateUserProfile(@Valid @RequestBody UpdateUserProfile request) {
        userService.updateUserProfile(SecurityUtils.getCurrentUserId(), request);
        return ResponseEntity.noContent().build();
    }

}
