package faang.school.notificationservice.listener.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.dto.event.EventStartReminderEvent;
import faang.school.notificationservice.listener.AbstractEventListener;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import faang.school.notificationservice.client.UserServiceClient;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class EventStartReminderEventListener extends AbstractEventListener<EventStartReminderEvent> implements MessageListener {


    public EventStartReminderEventListener(List<MessageBuilder<EventStartReminderEvent>> messageBuilders,
                                           ObjectMapper objectMapper,
                                           UserServiceClient userServiceClient,
                                           List<NotificationService> notificationServices) {
        super(messageBuilders, objectMapper, userServiceClient, notificationServices);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, EventStartReminderEvent.class, event -> {
            String text = getMessage(event, Locale.ENGLISH);
            event.getAttendeesIds().forEach(attendee -> sendNotification(attendee, text));
        });
    }
}
