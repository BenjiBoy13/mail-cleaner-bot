package personal.ben.functions.mailcleanerbot.model;

import java.time.Instant;

/**
 * Standard JSON envelope for HTTP responses.
 *
 * @param success   True if the operation succeeded, false otherwise.
 * @param message   Status message or human-readable explanation.
 * @param data      Payload data if successful (null on errors).
 * @param error     Error details if unsuccessful (null on success).
 * @param timestamp Instant of response generation.
 */
public record ApiResponse<T>(
        boolean success,
        String message,
        T data,
        String error,
        Instant timestamp
) {
    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(true, message, data, null, Instant.now());
    }

    public static <T> ApiResponse<T> error(String message, String errorDetails) {
        return new ApiResponse<>(false, message, null, errorDetails, Instant.now());
    }
}
