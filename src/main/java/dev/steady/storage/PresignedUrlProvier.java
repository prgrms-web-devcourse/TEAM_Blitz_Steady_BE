package dev.steady.storage;

import dev.steady.global.exception.InvalidValueException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.time.Duration;
import java.util.UUID;
import java.util.regex.Pattern;

import static dev.steady.storage.exception.StorageErrorCode.NOT_SUPPORTED_FILE_TYPE;

@Component
public class PresignedUrlProvier {

    private static final String CONTENT_TYPE_PATTERN = "image/%s";
    private static final String PERIOD = ".";
    private final String bucketName;
    private final S3Presigner s3Presigner;

    public PresignedUrlProvier(@Value("${cloud.s3.bucket}") String bucketName, S3Presigner s3Presigner) {
        this.bucketName = bucketName;
        this.s3Presigner = s3Presigner;
    }

    public String providePutObjectUrl(String fileName, String keyPattern) {

        String extension = fileName.substring(fileName.lastIndexOf(PERIOD) + 1);
        validateImageFile(extension);

        String key = String.format(keyPattern, UUID.randomUUID());
        String contentType = String.format(CONTENT_TYPE_PATTERN, extension.toLowerCase());

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .putObjectRequest(putObjectRequest)
                .signatureDuration(Duration.ofMinutes(3))
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

        return presignedRequest.url().toString();
    }

    private void validateImageFile(String extension) {
        String imageFileRegex = "^(jpeg|jpg|png)$";
        if (!Pattern.matches(imageFileRegex, extension)) {
            throw new InvalidValueException(NOT_SUPPORTED_FILE_TYPE);
        }
    }

}
