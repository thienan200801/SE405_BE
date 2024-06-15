package uit.se122.ieltstinder.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import uit.se122.ieltstinder.entity.User;
import uit.se122.ieltstinder.entity.User_;
import uit.se122.ieltstinder.entity.enumeration.UserStatus;
import uit.se122.ieltstinder.exception.BadRequestException;
import uit.se122.ieltstinder.service.ResourceService;
import uit.se122.ieltstinder.service.criteria.UserCriteria;
import uit.se122.ieltstinder.service.dto.UserAdminDto;
import uit.se122.ieltstinder.service.dto.request.UpdateUserProfile;
import uit.se122.ieltstinder.service.dto.response.AdminUserDetailResponse;
import uit.se122.ieltstinder.service.dto.response.UserProfileResponseDto;
import uit.se122.ieltstinder.service.mapper.UserMapper;
import uit.se122.ieltstinder.repository.UserRepository;
import uit.se122.ieltstinder.service.UserService;
import uit.se122.ieltstinder.service.dto.UserDto;
import uit.se122.ieltstinder.service.query.QueryService;

import java.util.Objects;

import static uit.se122.ieltstinder.constant.MessageConstant.USER_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends QueryService<User> implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final ResourceService resourceService;

    @Override
    public Page<UserAdminDto> getAllUsers(UserCriteria criteria, Pageable pageable) {
        Specification<User> specification = createSpecification(criteria);
        return userRepository.findAll(specification, pageable).map(userMapper::toUserAdminDto);
    }

    private Specification<User> createSpecification(UserCriteria criteria) {
        Specification<User> specification = Specification.where(null);
        if (criteria != null) {
            if (Objects.nonNull(criteria.getId())) {
                specification = specification.and(buildSpecification(criteria.getId(), User_.id));
            }

            if (Objects.nonNull(criteria.getEmail())) {
                specification = specification.and(buildSpecification(criteria.getEmail(), User_.email));
            }

            if (Objects.nonNull(criteria.getName())) {
                Specification<User> specificationCustom = buildSpecification(criteria.getName(),
                        root -> root.get(User_.firstName));
                specificationCustom = specificationCustom.or(buildSpecification(criteria.getName(),
                        root -> root.get(User_.lastName)));
                specification = specification.and(specificationCustom);
            }

            if (Objects.nonNull(criteria.getRole())) {
                specification = specification.and(buildSpecification(criteria.getRole(), User_.role));
            }

            if (Objects.nonNull(criteria.getStatus())) {
                specification = specification.and(buildSpecification(criteria.getStatus(), User_.status));
            }

            if (Objects.nonNull(criteria.getLevel())) {
                specification = specification.and(buildSpecification(criteria.getLevel(), User_.level));
            }
        }

        return specification;
    }

    @Override
    @Transactional
    public UserDto findUserById(Long userId) {
        User user = userRepository
                        .findById(userId)
                        .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));

        return userMapper.toUserDto(user);
    }

    @Override
    public UserProfileResponseDto getUserProfile(Long userId) {
        User user = userRepository
                        .findById(userId)
                        .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));

        return userMapper.toUserProfile(user);
    }

    @Override
    public Page<UserDto> getUserForRequest(Long userId, Pageable pageable) {
        return userRepository.findUsersNotInvolvedInRequestWithUserId(userId, pageable).map(userMapper::toUserDto);
    }

    @Override
    @Transactional
    public void blockUser(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));
        user.setStatus(UserStatus.BLOCKED);
    }

    @Override
    @Transactional
    public void unblockUser(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));
        user.setStatus(UserStatus.ACTIVE);
    }

    @Override
    public UserProfileResponseDto getProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));
        return userMapper.toUserProfile(user);
    }

    @Override
    @Transactional
    public void updateAvatar(Long userId, MultipartFile image) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));

        String url = resourceService.uploadImage(image);
        user.setAvatar(url);
    }

    @Override
    @Transactional
    public void updateUserProfile(Long userId, UpdateUserProfile request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        user.setGender(request.getGender());
        user.setAge(request.getAge());
    }

    @Override
    public AdminUserDetailResponse getAdminUserDetail(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));

        return userMapper.toAdminUserDetail(user);
    }

}
