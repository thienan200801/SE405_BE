package uit.se122.ieltstinder.service.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uit.se122.ieltstinder.entity.Post;
import uit.se122.ieltstinder.service.dto.PostDto;

@Component
@RequiredArgsConstructor
public class PostMapper {

    public PostDto toPostDto(Post post) {
        return new PostDto(
                "Post " + post.getId(),
                post.getId(),
                post.getName(),
                post.getUrl(),
                post.getCreatedDate(),
                post.getStatus(),
                post.getDuration()
        );
    }

}
