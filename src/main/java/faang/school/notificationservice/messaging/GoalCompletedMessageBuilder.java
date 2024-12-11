package faang.school.notificationservice.messaging;

import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.dto.goal.GoalCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class GoalCompletedMessageBuilder implements MessageBuilder<GoalCompletedEvent> {

    private final MessageSource messageSource;
    private final UserServiceClient userServiceClient;

    @Override
    public Class<?> getInstance() {
        return GoalCompletedEvent.class;
    }

    @Override
    public String buildMessage(GoalCompletedEvent event, Locale locale) {
        UserDto user = userServiceClient.getUser(event.getUserId());
        return messageSource.getMessage("goal_completed.new", new Object[]{user.getUsername()}, locale);
    }
}