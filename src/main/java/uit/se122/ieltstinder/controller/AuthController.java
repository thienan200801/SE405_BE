package uit.se122.ieltstinder.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uit.se122.ieltstinder.entity.User;
import uit.se122.ieltstinder.entity.enumeration.Role;
import uit.se122.ieltstinder.security.SecurityUtils;
import uit.se122.ieltstinder.service.AuthService;
import uit.se122.ieltstinder.service.UserService;
import uit.se122.ieltstinder.service.dto.request.AuthLoginRequestDto;
import uit.se122.ieltstinder.service.dto.request.AuthRegisterRequestDto;
import uit.se122.ieltstinder.service.dto.response.AuthLoginResponseDto;
import uit.se122.ieltstinder.service.dto.response.UserProfileResponseDto;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication Resources")
@RequestMapping(value = "/api")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<AuthLoginResponseDto> login(@Valid @RequestBody AuthLoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request, Role.USER));
    }

    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Void> register(@Valid @RequestBody AuthRegisterRequestDto request) {
        authService.register(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<UserProfileResponseDto> getProfile() {
        return ResponseEntity.ok(userService.getUserProfile(SecurityUtils.getCurrentUserId()));
    }

    @PostMapping(value = "/admin/login")
    public ResponseEntity<AuthLoginResponseDto> adminLogin(@Valid @RequestBody AuthLoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request, Role.ADMIN));
    }

}
