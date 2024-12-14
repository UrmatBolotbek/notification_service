package faang.school.notificationservice.messaging;

import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.FollowerEvent;
import faang.school.notificationservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Component
public class FollowerMessageBuilder implements MessageBuilder<FollowerEvent> {
    private final UserServiceClient userServiceClient;
    private final MessageSource messageSource;

    @Override
    public String buildMessage(FollowerEvent event, Locale locale) {
        UserDto followee = userServiceClient.getUser(event.getFolloweeId());
        UserDto follower = userServiceClient.getUser(event.getFollowerId());

        return messageSource.getMessage(
                "follower.new",
                new Object[]{followee.getUsername(), follower.getUsername()},
                locale
        );
    }

    @Override
    public Class<?> getInstance() {
        return FollowerEvent.class;
    }
}
