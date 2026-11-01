package personal.ben.functions.mailcleanerbot;

import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personal.ben.functions.mailcleanerbot.model.ApiResponse;
import personal.ben.functions.mailcleanerbot.service.MailCleanerService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.Instant;

/**
 * Entry point for Google Cloud Functions (HTTP Trigger).
 * Supports both Cloud Run functions runtime and local execution via the Functions Framework.
 */
public class MailCleanerFunction implements HttpFunction {

    private static final Logger logger = LoggerFactory.getLogger(MailCleanerFunction.class);

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Instant.class, (JsonSerializer<Instant>) (src, _, _) ->
                    new JsonPrimitive(src.toString()))
            .setPrettyPrinting()
            .create();

    private final MailCleanerService cleanerService = new  MailCleanerService();

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        response.setContentType("application/json; charset=utf-8");

        logger.info("=== Starting: Mail Cleaner Bot Function Execution === ");

        String method = request.getMethod();
        logger.info("Received {} request from {}", method, request.getUri());

        try {
            cleanerService.run();
            response.setStatusCode(HttpURLConnection.HTTP_OK);
            writeJson(response, ApiResponse.ok("Mail cleanup executed successfully", null));
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request parameters: {}", e.getMessage());
            response.setStatusCode(HttpURLConnection.HTTP_BAD_REQUEST);
            writeJson(response, ApiResponse.error("Bad request", e.getMessage()));
        } catch (Exception e) {
            logger.error("Unhandled error during function execution: {}", e.getMessage(), e);
            response.setStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR);
            writeJson(response, ApiResponse.error("Internal server error", e.getMessage()));
        }
    }

    private void writeJson(HttpResponse response, Object data) throws IOException {
        BufferedWriter writer = response.getWriter();
        writer.write(GSON.toJson(data));
        writer.newLine();
        writer.flush();
    }

}
