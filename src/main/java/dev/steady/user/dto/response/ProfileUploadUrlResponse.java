package dev.steady.user.dto.response;

public record ProfileUploadUrlResponse(
        String url
) {
    
    public static ProfileUploadUrlResponse from(String url) {
        return new ProfileUploadUrlResponse(url);
    }

}
