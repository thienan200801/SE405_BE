package uit.se122.ieltstinder.config;

import io.awspring.cloud.autoconfigure.core.CredentialsProperties;
import io.awspring.cloud.autoconfigure.core.RegionProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class AWSConfiguration {

    private final CredentialsProperties credentialsProperties;
    private final RegionProperties regionProperties;

    private AwsCredentialsProvider awsCredentialsProvider() {
        return () -> new AwsCredentials() {
            @Override
            public String accessKeyId() {
                return credentialsProperties.getAccessKey();
            }
            @Override
            public String secretAccessKey() {
                return credentialsProperties.getSecretKey();
            }
        };
    }

    @Bean
    public S3Client initializeAmazonS3Client() {
        return S3Client.builder()
                .credentialsProvider(awsCredentialsProvider())
                .region(Region.of(Objects.requireNonNull(regionProperties.getStatic())))
                .build();
    }

    public S3Presigner initializeAmazonS3Presigner() {

        return S3Presigner.builder()
                .credentialsProvider(awsCredentialsProvider())
                .region(Region.of(Objects.requireNonNull(regionProperties.getStatic())))
                .build();
    }

}
