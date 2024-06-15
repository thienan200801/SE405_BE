package uit.se122.ieltstinder.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uit.se122.ieltstinder.config.ApplicationProperties;
import uit.se122.ieltstinder.service.ResourceService;
import uit.se122.ieltstinder.service.S3Service;
import uit.se122.ieltstinder.util.FileUtils;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {

    private final S3Service s3Service;

    @Override
    public String uploadAudio(MultipartFile audio) {
        byte[] bytes = FileUtils.checkAudioFile(audio);
        String key = "audio" + "-" + UUID.randomUUID() + ".mp3";

        return s3Service.uploadFile(key,"ieltstinder", bytes, List.of("audio"), audio.getContentType());
    }

    @Override
    public String uploadImage(MultipartFile image) {
        byte[] bytes = FileUtils.checkFile(image);
        String key = "audio" + "-" + UUID.randomUUID() + ".png";

        return s3Service.uploadFile(key,"ieltstinder", bytes, List.of("image"), image.getContentType());
    }

    @Override
    public String uploadVideo(MultipartFile video) {
        byte[] bytes = FileUtils.checkVideoFile(video);
        String key = "video" + "-" + UUID.randomUUID() + ".mp4";
        return s3Service.uploadFile(key, "ieltstinder", bytes, List.of("video"), video.getContentType());
    }
}
