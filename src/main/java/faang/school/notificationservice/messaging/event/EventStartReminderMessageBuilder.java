package faang.school.notificationservice.messaging.event;

import faang.school.notificationservice.dto.event.EventStartReminderEvent;
import faang.school.notificationservice.messaging.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class EventStartReminderMessageBuilder implements MessageBuilder<EventStartReminderEvent> {

    private final MessageSource messageSource;

    @Override
    public Class<?> getInstance() {
        return EventStartReminderEvent.class;
    }

    @Override
    public String buildMessage(EventStartReminderEvent event, Locale locale) {
        String messageKey = switch (event.getReminderType()) {
            case "1day" -> "event.reminder.1day";
            case "5hours" -> "event.reminder.5hours";
            case "1hour" -> "event.reminder.1hour";
            case "10minutes" -> "event.reminder.10minutes";
            default -> throw new IllegalArgumentException("Unknown reminder type: " + event.getReminderType());
        };
        return messageSource.getMessage(messageKey, null, locale);
    }
}
