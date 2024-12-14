package faang.school.notificationservice.listener.achievement;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.event.AchievementEvent;
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
public class AchievementEventListener extends AbstractEventListener<AchievementEvent> implements MessageListener {

    public AchievementEventListener(List<MessageBuilder<AchievementEvent>> messageBuilders,
                                    ObjectMapper objectMapper,
                                    UserServiceClient userServiceClient,
                                    List<NotificationService> notificationServices) {
        super(messageBuilders, objectMapper, userServiceClient, notificationServices);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, AchievementEvent.class, event -> {
            String messageText = getMessage(event.getUserId(), event);
            sendNotification(event.getUserId(), messageText);
        });
    }
}
