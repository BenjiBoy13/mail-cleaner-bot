package personal.ben.functions.mailcleanerbot.factory;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.UserCredentials;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public final class GoogleServiceFactory {

    private static final Logger logger = LoggerFactory.getLogger(GoogleServiceFactory.class);

    private GoogleServiceFactory() {
    }

    public static Optional<GoogleAuthorizationCodeFlow> createGoogleAuthorizationCodeFlow(String clientId, String clientSecret, List<String> scopes) {
        try {
            GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), clientId,
                    clientSecret, scopes).setAccessType("offline").build();

            return Optional.of(googleAuthorizationCodeFlow);
        } catch (Exception e) {
            logger.error("Error creating Google AuthorizationCodeFlow object", e);
            return Optional.empty();
        }
    }

    public static Optional<Gmail> createGmailService(String clientId, String clientSecret, String refreshToken) {
        try {
            UserCredentials credentials = UserCredentials.newBuilder()
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setRefreshToken(refreshToken)
                    .build();

            Gmail gmail = new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), new HttpCredentialsAdapter(credentials))
                    .setApplicationName("Mail Cleaner Bot")
                    .build();

            return Optional.of(gmail);
        } catch (Exception e) {
            logger.error("Error creating Gmail service", e);
            return Optional.empty();
        }
    }

}
