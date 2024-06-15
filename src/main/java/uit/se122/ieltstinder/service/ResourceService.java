package uit.se122.ieltstinder.service;

import org.springframework.web.multipart.MultipartFile;

public interface ResourceService {

    String uploadAudio(MultipartFile audio);
    String uploadImage(MultipartFile image);
    String uploadVideo(MultipartFile video);

}
