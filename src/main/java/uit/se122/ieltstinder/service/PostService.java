package uit.se122.ieltstinder.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import uit.se122.ieltstinder.service.criteria.PostCriteria;
import uit.se122.ieltstinder.service.dto.PostDto;


public interface PostService {

    Page<PostDto> getAllPosts(PostCriteria criteria, Pageable pageable);
    void uploadPost(Long userId, MultipartFile video, String name);

}
