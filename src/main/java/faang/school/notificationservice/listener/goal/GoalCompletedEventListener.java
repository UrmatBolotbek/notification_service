package faang.school.notificationservice.listener.goal;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.goal.GoalCompletedEvent;
import faang.school.notificationservice.listener.AbstractEventListener;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class GoalCompletedEventListener extends AbstractEventListener<GoalCompletedEvent> implements MessageListener {

    public GoalCompletedEventListener(List<MessageBuilder<GoalCompletedEvent>> messageBuilders,
                                      ObjectMapper objectMapper,
                                      UserServiceClient userServiceClient,
                                      List<NotificationService> notificationServices) {
        super(messageBuilders, objectMapper, userServiceClient, notificationServices);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, GoalCompletedEvent.class, event -> {
            String text = getMessage(event, Locale.UK);
            sendNotification(event.getUserId(), text);
        });
    }
}