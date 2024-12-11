package faang.school.notificationservice.listener.subscribtion;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.FollowerEvent;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.listener.AbstractEventListener;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FollowerEventListener extends AbstractEventListener<FollowerEvent> implements MessageListener {

    public FollowerEventListener(List<MessageBuilder<FollowerEvent>> messageBuilders,
                                 ObjectMapper objectMapper,
                                 UserServiceClient userServiceClient,
                                 List<NotificationService> notificationServices) {
        super(messageBuilders, objectMapper, userServiceClient, notificationServices);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, FollowerEvent.class, event -> {
            String messageText = getMessage(event.getFolloweeId(), event);
            sendNotification(event.getFolloweeId(), messageText);
        });
    }
}
