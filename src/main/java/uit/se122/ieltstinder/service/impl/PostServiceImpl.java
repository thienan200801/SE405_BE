package uit.se122.ieltstinder.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uit.se122.ieltstinder.entity.Post;
import uit.se122.ieltstinder.entity.Post_;
import uit.se122.ieltstinder.entity.User;
import uit.se122.ieltstinder.entity.User_;
import uit.se122.ieltstinder.entity.enumeration.PostStatus;
import uit.se122.ieltstinder.exception.BadRequestException;
import uit.se122.ieltstinder.repository.UserRepository;
import uit.se122.ieltstinder.service.ResourceService;
import uit.se122.ieltstinder.service.criteria.PostCriteria;
import uit.se122.ieltstinder.service.mapper.PostMapper;
import uit.se122.ieltstinder.repository.PostRepository;
import uit.se122.ieltstinder.service.PostService;
import uit.se122.ieltstinder.service.dto.PostDto;
import uit.se122.ieltstinder.service.query.QueryService;

import java.time.Instant;
import java.util.Objects;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static uit.se122.ieltstinder.constant.MessageConstant.USER_NOT_EXIST;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl extends QueryService<Post> implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final ResourceService resourceService;
    private final UserRepository userRepository;

    @Override
    public Page<PostDto> getAllPosts(PostCriteria criteria, Pageable pageable) {
        Specification<Post> specification = createSpecification(criteria);
        return postRepository.findAll(specification, pageable).map(postMapper::toPostDto);
    }

    private Specification<Post> createSpecification(PostCriteria criteria) {
        Specification<Post> specification = Specification.where(null);

        if (criteria != null) {
            if (Objects.nonNull(criteria.getName())) {
                specification = specification.and(buildSpecification(criteria.getName(), Post_.name));
            }

            if (Objects.nonNull(criteria.getStatus())) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Post_.status));
            }

            if (Objects.nonNull(criteria.getUserId())) {
                specification = specification.and(buildSpecification(criteria.getUserId(),
                        root -> root.get(Post_.user).get(User_.id)));
            }
        }

        return specification;
    }

    @Override
    @Transactional
    public void uploadPost(Long userId, MultipartFile video, String name) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadRequestException(USER_NOT_EXIST));
        String url = resourceService.uploadVideo(video);
        try {
            Long duration = getVideoDuration(video);
            postRepository.save(Post
                    .builder()
                    .name(name)
                    .url(url)
                    .status(PostStatus.PENDING)
                    .user(user)
                    .createdDate(Instant.now())
                    .duration(duration)
                    .build());
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    private long getVideoDuration(MultipartFile video) throws IOException {
        // Save the video file temporarily
        Path tempFile = Files.createTempFile("temp", video.getOriginalFilename());
        try (InputStream inputStream = video.getInputStream()) {
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        }

        // Get the file size in bytes
        long fileSize = Files.size(tempFile);

        // Estimate duration based on average bitrate (adjust this value based on your needs)
        int averageBitrate = 348; // For example, 500 kbps

        return (fileSize * 8) / (averageBitrate * 1000);
    }

}
