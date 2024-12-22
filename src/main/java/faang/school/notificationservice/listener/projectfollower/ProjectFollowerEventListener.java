package faang.school.notificationservice.listener.projectfollower;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.event.ProjectFollowerEvent;
import faang.school.notificationservice.listener.AbstractEventListener;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectFollowerEventListener extends AbstractEventListener<ProjectFollowerEvent> implements MessageListener {
    public ProjectFollowerEventListener(List<MessageBuilder<ProjectFollowerEvent>> messageBuilders,
                                        ObjectMapper objectMapper, UserServiceClient userServiceClient,
                                        List<NotificationService> notificationServices) {
        super(messageBuilders, objectMapper, userServiceClient, notificationServices);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        handleEvent(message, ProjectFollowerEvent.class, event -> {
            Long userId = event.getCreatorId();
            String text = getMessage(userId, event);
            sendNotification(event.getCreatorId(), text);
        });
    }
}
