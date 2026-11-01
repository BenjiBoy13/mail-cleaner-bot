package personal.ben.functions.mailcleanerbot.service;

import com.google.api.services.gmail.Gmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import personal.ben.functions.mailcleanerbot.factory.GoogleServiceFactory;

import java.io.IOException;

public class MailCleanerService {

    private static final Logger logger = LoggerFactory.getLogger(MailCleanerService.class);

    private static final String CLIENT_ID = System.getenv("CLIENT_ID");

    private static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");

    private static final String REFRESH_TOKEN = System.getenv("REFRESH_TOKEN");

    private static final String USER_ID = "me";

    public void run() throws IOException {
        logger.info("Confirming authentication and creating SDK Connection");

        Gmail gmail = GoogleServiceFactory.createGmailService(CLIENT_ID, CLIENT_SECRET, REFRESH_TOKEN).orElse(null);

        if (gmail == null) {
            throw new IllegalStateException("Gmail service not available");
        }

        Gmail.Users.Messages.List messagesList = gmail.users().messages().list(USER_ID);
        messagesList.execute().getMessages().forEach(message -> {
            logger.info("Message Thread Id: {}", message.getThreadId());
        });
    }

}
