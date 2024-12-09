package faang.school.notificationservice.messaging.event;

import faang.school.notificationservice.dto.event.EventStartEvent;
import faang.school.notificationservice.messaging.MessageBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class EventStartMessageBuilder implements MessageBuilder<EventStartEvent> {

    private final MessageSource messageSource;

    @Override
    public Class<?> getInstance() {
        return EventStartEvent.class;
    }

    @Override
    public String buildMessage(EventStartEvent event, Locale locale) {
        return messageSource.getMessage("event.start", null, locale);
    }
}
