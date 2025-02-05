# Notification Service

**Notification Service** is a microservice within our integrated application ecosystem that is responsible for delivering notifications to users via multiple channels. It processes events from other services (such as achievement events from achievement_service) and sends notifications using Email, SMS, or Telegram based on user preferences.

---

## Overview

- **Abstract Event Listener:**  
  A base class (`AbstractEventListener`) provides common logic for handling incoming events from Redis (in JSON format). It leverages an `ObjectMapper` for JSON-to-Java conversion, a `UserServiceClient` for fetching user details, and a collection of `NotificationService` and `MessageBuilder` implementations to compose and dispatch notifications.

- **Multi-Channel Notification Delivery:**  
  The service supports:
  - **Email Notifications:** Integrated with Google SMTP, sending emails using a dedicated `EmailService` that implements `NotificationService`.
  - **SMS Notifications:** Uses Vonage (Nexmo) for sending SMS messages via an `SmsService` that also implements `NotificationService`.
  - **Telegram Notifications:** Sends messages through a custom Telegram bot via a `TelegramService`, which implements `NotificationService`.

- **Configuration & Extensibility:**  
  All notification channels are configured via `application.yaml`. The service is designed to be extensible; new notification channels can be added simply by implementing the `NotificationService` interface and configuring them accordingly.

- **Robust Testing:**  
  Each component—ranging from the abstract event listener to the individual notification services (Email, SMS, Telegram)—is covered by unit tests to ensure reliable message processing and delivery.

---

## Technologies Used

- [Spring Boot](https://spring.io/projects/spring-boot) – Main framework for building the service.
- [PostgreSQL](https://www.postgresql.org/) – Primary database (managed separately in our infrastructure).
- [Redis](https://redis.io/) – Used for caching and as a pub/sub messaging system.
- [Testcontainers](https://testcontainers.com/) – For integration testing with real database and Redis instances.
- [Liquibase](https://www.liquibase.org/) – For managing database schema migrations.
- [Gradle](https://gradle.org/) – Build system.
- [Lombok](https://projectlombok.org/) – Simplifies POJO creation.
- [MapStruct](https://mapstruct.org/) – For efficient object mapping.
- **External APIs & SDKs:**
  - Google SMTP for Email notifications.
  - Vonage (Nexmo) for SMS notifications.
  - Telegram SDK for Telegram bot integration.

---
