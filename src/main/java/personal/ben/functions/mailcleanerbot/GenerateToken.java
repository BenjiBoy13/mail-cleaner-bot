package personal.ben.functions.mailcleanerbot;

import java.util.List;

import com.google.api.services.gmail.GmailScopes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

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

        try {

            GoogleAuthorizationCodeFlow googleAuthorizationCodeFlow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), CLIENT_ID,
                    CLIENT_SECRET, SCOPES).setAccessType("offline").build();

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
