package uit.se122.ieltstinder.util;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;
import uit.se122.ieltstinder.exception.BadRequestException;
import uit.se122.ieltstinder.exception.InternalException;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static uit.se122.ieltstinder.constant.MessageConstant.*;

public final class FileUtils {

    private static final List<String> AUDIO_TYPES = List.of("mp3");
    private static final List<String> IMAGE_TYPES = List.of("png","jpg","jpeg");
    private static final List<String> VIDEO_TYPES = List.of("mp4","mov");
    private static final Long MAX_IMAGE_SIZE = 2L;
    private static final Long MAX_AUDIO_SIZE = 20L;
    private static final Long MAX_VIDEO_SIZE = 30L;

    private FileUtils() {}

    public static String getExtension(String mimeType) {
        return switch (mimeType) {
            case "image/jpeg" -> "jpeg";
            case "image/png" -> "png";
            case "application/pdf" -> "pdf";
            case "video/mp4" -> "mp4";
            case "video/webm" -> "webm";
            case "video/quicktime" -> "mov";
            case "audio/mpeg" -> "mp3";
            default -> throw new UnsupportedMediaTypeStatusException("");
        };
    }

    public static String getFilename(String name, String mimeType) {
        return name;
    }

    public static Long getSize(Long size){
        return size/(1024 * 1024);
    }

    public static byte[] checkFile(MultipartFile file){
        try {
            String contentType = file.getContentType();
            if(Objects.nonNull(contentType) && !IMAGE_TYPES.contains(FileUtils.getExtension(contentType))){
                throw new BadRequestException(IMAGE_TYPE_INVALID_ERROR);
            }
            if(FileUtils.getSize(file.getSize()) > MAX_IMAGE_SIZE){
                throw new BadRequestException(IMAGE_SIZE_INVALID_ERROR);
            }
            return file.getBytes();
        }catch (IOException e){
            throw new InternalException();
        }
    }

    public static byte[] checkAudioFile(MultipartFile file){
        try {
            String contentType = file.getContentType();
            if(Objects.nonNull(contentType) && !AUDIO_TYPES.contains(FileUtils.getExtension(contentType))){
                throw new BadRequestException(AUDIO_TYPE_INVALID_ERROR);
            }
            if(FileUtils.getSize(file.getSize()) > MAX_AUDIO_SIZE){
                throw new BadRequestException(AUDIO_SIZE_INVALID_ERROR);
            }
            return file.getBytes();
        } catch (IOException e){
            throw new InternalException();
        }
    }

    public static byte[] checkVideoFile(MultipartFile file) {
        try {
            String contentType = file.getContentType();
            if(Objects.nonNull(contentType) && !VIDEO_TYPES.contains(FileUtils.getExtension(contentType))){
                throw new BadRequestException(VIDEO_TYPE_INVALID_ERROR);
            }
            if(FileUtils.getSize(file.getSize()) > MAX_VIDEO_SIZE){
                throw new BadRequestException(VIDEO_SIZE_INVALID_ERROR);
            }
            return file.getBytes();
        } catch (IOException e){
            throw new InternalException();
        }
    }

    public static void checkMultipartFileNull(MultipartFile file, String message) {
        if (file.isEmpty()) {
            throw new BadRequestException(message);
        }
    }

}
