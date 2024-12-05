package faang.school.notificationservice.messaging;

import faang.school.notificationservice.listener.FollowerEventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;

@Slf4j
public class FollowerMessageBuilder implements MessageBuilder<FollowerEventListener> {

    @Override
    public Class<?> getInstance() {
        return null;
    }

    @Override
    public String buildMessage(FollowerEventListener event, Locale locale) {
        log.info(event.toString());
       return event.toString();
    }
}
