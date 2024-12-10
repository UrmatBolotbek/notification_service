package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Slf4j
public abstract class AbstractEventListener<T> {

    private final List<MessageBuilder<T>> messageBuilders;
    private final ObjectMapper objectMapper;
    private final UserServiceClient userServiceClient;
    private final List<NotificationService> notificationServices;

    protected void handleEvent(Message message, Class<T> clazz, Consumer<T> consumer) {
        try {
            log.info("Received message: {}", new String(message.getBody()));
            T event = objectMapper.readValue(message.getBody(), clazz);
            consumer.accept(event);
        } catch (IOException e) {
            log.error("Error deserializing JSON to object", e);
            throw new RuntimeException("Error deserializing JSON to object", e);
        }
    }

    protected String getMessage(T event, Locale userLocale) {
        log.info("Building message for event of type: {} with locale: {}", event.getClass().getName(), userLocale);
        return messageBuilders.stream()
                .filter(messageBuilder -> messageBuilder.getInstance() == event.getClass())
                .findFirst()
                .map(messageBuilder -> messageBuilder.buildMessage(event, userLocale))
                .orElseThrow(() -> {
                    log.error("No message builder found for the given event type: {}", event.getClass().getName());
                    return new IllegalArgumentException("No message builder found for the given event type: "
                            + event.getClass().getName());
                });
    }

    protected void sendNotification(Long id, String message) {
        log.info("Fetching user details for user ID: {}", id);

        UserDto user = userServiceClient.getUser(id);
        log.info("User details retrieved: {}", user);

        notificationServices.stream()
                .filter(notificationService -> notificationService.getPreferredContact().equals(user.getPreference()))
                .findFirst()
                .orElseThrow(() -> {
                    log.error("No notification service found for the user's preferred communication method: {}", user.getPreference());
                    return new IllegalArgumentException("No notification service found for the user's preferred communication method: "
                            + user.getPreference());
                })
                .send(user, message);
        log.info("Notification successfully sent to user ID: {}", id);
    }
}
