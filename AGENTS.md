# AGENTS.md

## Project Context
- **Name:** Mail Cleaner Bot
- **Description:** This Google cloud function propose is to connect to a user's personal Gmail account and clean up and organize emails based on retentionPolicies and organization rules defined in a configuration file
- **Type:** Google Cloud Function (2nd Gen)
- **Runtime:** Java 25
- **Framework:** Google Cloud Functions Framework for Java (`com.google.cloud.functions:functions-framework-api:1.1.0`)
- **Trigger Type:** HTTP Trigger (implements `com.google.cloud.functions.HttpFunction`)
  *(Note: CloudEvents functions implement `com.google.cloud.functions.CloudEventsFunction`)*
- **Build Tool:** Maven 3.9+ (NO wrapper provided, must use `mvn` on terminal)

---

## Directory Structure

```
├── pom.xml
└── src/
├── main/
│   └── java/
│       └── personal/ben/functions/mailcleanerbot
│           ├── MailCleanerFunction.java   # Function entrypoint
│           ├── GenerateToken.java/        # Main Java entry point meant to be locally executed to get a refresh token
│           ├── factory/                   # SDK Client factory & other factories to be created
│           ├── model/                     # Request/Response POJOs
│           └── service/                   # Business logic
└── test/
└── java/
└── personal/ben/functions/mailcleanerbot/
```