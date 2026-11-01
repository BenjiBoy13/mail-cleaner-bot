package personal.ben.functions.mailcleanerbot;

import java.util.List;

import com.google.api.services.gmail.GmailScopes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import personal.ben.functions.mailcleanerbot.factory.GoogleServiceFactory;

public class GenerateToken {

    private static final Logger logger = LoggerFactory.getLogger(GenerateToken.class);

    private static final String CLIENT_ID = System.getenv("CLIENT_ID");

    private static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");

    private static final List<String> SCOPES = List.of(
            GmailScopes.GMAIL_LABELS,
            GmailScopes.GMAIL_METADATA,
            GmailScopes.GMAIL_SETTINGS_BASIC,
            "https://www.googleapis.com/auth/gmail.messages"
    );

    static void main(String[] args) {
        logger.info("=== Starting Refresh Token Generation ===");

        if (CLIENT_ID == null || CLIENT_ID.isBlank() || CLIENT_SECRET == null ||  CLIENT_SECRET.isBlank()) {
            logger.error("Client ID or Secret was not provided");
            logger.info("=== Done ===");
            return;
        }

        try {
            GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = GoogleServiceFactory.createGoogleAuthorizationCodeFlow(
                    CLIENT_ID, CLIENT_SECRET, SCOPES
            ).orElse(null);

            if (googleAuthorizationCodeFlow == null) {
                logger.error("Google Authorization Code Flow could not be created");
                logger.info("=== Done ===");
                return;
            }

            LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
            Credential credential = new AuthorizationCodeInstalledApp(googleAuthorizationCodeFlow, receiver)
                    .authorize("user");

            String refreshTokenMsg = "Refresh Token: %s".formatted(credential.getRefreshToken());
            logger.info(refreshTokenMsg);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        logger.info("=== Done ===");
    }

}
