package uit.se122.ieltstinder.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import uit.se122.ieltstinder.service.criteria.UserCriteria;
import uit.se122.ieltstinder.service.dto.UserAdminDto;
import uit.se122.ieltstinder.service.dto.UserDto;
import uit.se122.ieltstinder.service.dto.request.UpdateUserProfile;
import uit.se122.ieltstinder.service.dto.response.AdminUserDetailResponse;
import uit.se122.ieltstinder.service.dto.response.UserProfileResponseDto;

public interface UserService {
    Page<UserAdminDto> getAllUsers(UserCriteria criteria, Pageable pageable);
    UserDto findUserById(Long id);
    UserProfileResponseDto getUserProfile(Long userId);
    Page<UserDto> getUserForRequest(Long userId, Pageable pageable);
    void blockUser(Long userId);
    void unblockUser(Long userId);
    UserProfileResponseDto getProfile(Long userId);
    void updateAvatar(Long userId, MultipartFile image);
    void updateUserProfile(Long userId, UpdateUserProfile request);
    AdminUserDetailResponse getAdminUserDetail(Long userId);
}
