package faang.school.notificationservice.listener.recommendation;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.event.RecommendationReceivedEvent;
import faang.school.notificationservice.listener.AbstractEventListener;
import faang.school.notificationservice.messaging.MessageBuilder;

import faang.school.notificationservice.service.NotificationService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class RecommendationReceivedEventListener extends AbstractEventListener<RecommendationReceivedEvent> implements MessageListener {

    public RecommendationReceivedEventListener(ObjectMapper objectMapper,
                                               List<MessageBuilder<RecommendationReceivedEvent>> messageBuilders,
                                               UserServiceClient userServiceClient,
                                               List<NotificationService> notificationServices) {
        super(objectMapper, messageBuilders, userServiceClient, notificationServices);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, RecommendationReceivedEvent.class, event -> {
            String text = getMessage(event, Locale.UK);
            sendNotification(event.getReceiverId(), text);
        });
    }
}
