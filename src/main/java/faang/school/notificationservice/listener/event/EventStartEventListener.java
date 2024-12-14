package faang.school.notificationservice.listener.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.dto.event.EventStartEvent;
import faang.school.notificationservice.dto.event.EventStartReminderEvent;
import faang.school.notificationservice.listener.AbstractEventListener;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class EventStartEventListener extends AbstractEventListener<EventStartEvent> implements MessageListener {

    public EventStartEventListener(List<MessageBuilder<EventStartEvent>> messageBuilders,
                                   ObjectMapper objectMapper,
                                   UserServiceClient userServiceClient,
                                   List<NotificationService> notificationServices) {
        super(messageBuilders, objectMapper, userServiceClient, notificationServices);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, EventStartEvent.class, event -> event.getAttendeesIds().forEach(attendee -> {
            String text = getMessage(attendee, event);
            sendNotification(attendee, text);
        }));
    }
}
