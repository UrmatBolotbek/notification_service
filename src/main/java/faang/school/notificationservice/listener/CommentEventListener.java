package faang.school.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.CommentEvent;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class CommentEventListener extends AbstractEventListener<CommentEvent> implements MessageListener {

    public CommentEventListener(ObjectMapper objectMapper,
                                UserServiceClient userServiceClient,
                                List<MessageBuilder<CommentEvent>> messageBuilders,
                                List<NotificationService> notificationServices) {
        super(objectMapper, userServiceClient, messageBuilders, notificationServices);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, CommentEvent.class, event -> {
            String text = getMessage(event, Locale.UK);
            sendNotification(event.getPostAuthorId(), text);
        });
    }
}