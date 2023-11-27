package dev.steady.user.dto.response;

public record PutObjectUrlResponse(
        String presignedUrl,
        String objectUrl
) {

    public static PutObjectUrlResponse of(String presignedUrl, String objectUrl) {
        return new PutObjectUrlResponse(presignedUrl, objectUrl);
    }

}
