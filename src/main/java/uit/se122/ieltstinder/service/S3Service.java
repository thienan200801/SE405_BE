package uit.se122.ieltstinder.service;

import java.util.List;

public interface S3Service {

    String uploadFile(String key, String bucket, byte[] bytes, List<String> subFolders, String mimeType);
    String generateS3Url(String key, String bucket);
    String generateS3Url(String key,List<String> subFolders, String bucket);

}
