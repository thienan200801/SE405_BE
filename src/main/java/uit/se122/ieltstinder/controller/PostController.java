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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uit.se122.ieltstinder.security.SecurityUtils;
import uit.se122.ieltstinder.service.PostService;
import uit.se122.ieltstinder.service.criteria.PostCriteria;
import uit.se122.ieltstinder.service.dto.PostDto;
import uit.se122.ieltstinder.util.PaginationUtils;

import java.util.List;

@RequestMapping(value = "/api/posts")
@RestController
@RequiredArgsConstructor
@Tag(name = "Post Resources")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts(PostCriteria criteria, @ParameterObject @PageableDefault Pageable pageable) {
        final Page<PostDto> page = postService.getAllPosts(criteria, pageable);
        final HttpHeaders headers = PaginationUtils
                .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Void> uploadPost(@RequestPart("video") MultipartFile video,
                                           @RequestPart("name") String name) {
        postService.uploadPost(SecurityUtils.getCurrentUserId(), video, name);
        return ResponseEntity.noContent().build();
    }

}
