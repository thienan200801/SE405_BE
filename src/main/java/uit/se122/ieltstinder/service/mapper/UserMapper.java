package uit.se122.ieltstinder.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uit.se122.ieltstinder.entity.User;
import uit.se122.ieltstinder.service.dto.UserAdminDto;
import uit.se122.ieltstinder.service.dto.UserDto;
import uit.se122.ieltstinder.service.dto.response.AdminUserDetailResponse;
import uit.se122.ieltstinder.service.dto.response.UserProfileResponseDto;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PostMapper postMapper;

    public UserDto toUserDto(User user) {
        return new UserDto(
          user.getId(),
          user.getFirstName(),
          user.getLastName(),
          user.getAddress(),
          user.getGender() == 1 ? "Male" : "Female",
          user.getAge(),
          user.getDescription(),
          user.getAvatar(),
          user.getStatus(),
          user.getRole(),
          user.getLevel()
        );
    }

    public UserProfileResponseDto toUserProfile(User user) {
        return new UserProfileResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getGender() == 1 ? "Male" : "Female",
                user.getAge(),
                user.getDescription(),
                user.getAvatar(),
                user.getVideoSdkToken(),
                user.getLevel()
        );
    }

    public UserAdminDto toUserAdminDto(User user) {
        return new UserAdminDto(
                "User " + user.getId(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getGender() == 1 ? "Male" : "Female",
                user.getAge(),
                user.getDescription(),
                user.getAvatar(),
                user.getStatus(),
                user.getRole(),
                user.getLevel()
        );
    }

    public AdminUserDetailResponse toAdminUserDetail(User user) {
        return new AdminUserDetailResponse(
                "User " + user.getId(),
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getAddress(),
                user.getGender() == 1 ? "Male" : "Female",
                user.getAge(),
                user.getDescription(),
                user.getAvatar(),
                user.getStatus(),
                user.getLevel(),
                user.getPosts().stream().map(postMapper::toPostDto).toList()
        );
    }

}
