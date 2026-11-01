# Mail Cleaner Bot (Google Cloud Function)

A robust, production-ready **Google Cloud Java Function** (Cloud Run functions) designed to run both locally for development 
and in Google Cloud to organize a user's personal inbox on a personal Gmail account.

> Keep in mind the project is still **in development and not ready**

---

### Key Highlights
- **Decoupled Architecture:** `MailCleanerFunction` only handles HTTP protocol concerns (headers, query params, JSON body parsing, error status codes). Business logic resides in `MailCleanerService`, making it easy to test, swap, or trigger via events (CloudEvent / Pub/Sub) without altering business logic.
- **Modern Java 25 & Records:** Leverages immutable Java records (`CleanerOptions`, `CleanerResult`, `ApiResponse`) for lightweight DTOs.
- **Local Development Parity:** Employs the official Google Cloud `function-maven-plugin` to run the exact same Functions Framework invoker server locally on `http://localhost:8080/`.

---

## 🚀 Running Locally

### Generate your refresh token

After configuring your application on Google and getting your ClientID and Client Secret, run the 
main method on `personal.ben.functions.mailcleaner.GenerateToken`, follow the authorization flow in your browser
and grab your refresh token, set it as an environment variable to run the function locally or in the cloud.

### Kickstart the local server

### Option 1: Using the PowerShell script (Windows)
```powershell
.\run-local.ps1
```
Or specify a custom port:
```powershell
.\run-local.ps1 -Port 8081
```

### Option 2: Using Maven directly
```bash
mvn compile function:run
```

Once running, the Functions Framework server listens on `http://localhost:8080/`.

### Testing Locally

### 1. Trigger via HTTP GET
```bash
# Default options (dryRun=true, maxEmails=100)
curl http://localhost:8080/
```

In PowerShell:
```powershell
Invoke-RestMethod -Uri "http://localhost:8080" | ConvertTo-Json -Depth 5
```
---

## ☁️ Deploying to Google Cloud

When ready to deploy as a **Cloud Run function (2nd gen)**:

```bash
gcloud functions deploy mail-cleaner-bot \
  --gen2 \
  --runtime java21 \
  --entry-point personal.ben.functions.mailcleanerbot.MailCleanerFunction \
  --trigger-http \
  --allow-unauthenticated \
  --region us-central1 \
  --set-env-vars DRY_RUN=true,MAX_EMAILS=100
```

> **Note:** Cloud Scheduler can easily trigger this function periodically using an HTTP trigger (with OIDC authentication enabled in production).

---

## 🔐 Environment Variables

| Variable | Default | Description |
|---|---|---|
| `CLIENT_ID` | *(none)* | Google Cloud OAuth 2.0 Client ID. |
| `CLIENT_SECRET` | *(none)* | Google Cloud OAuth 2.0 Client Secret. |
| `REFRESH_TOKEN` | *(none)* | OAuth Refresh Token generated via `GenerateToken`. |
