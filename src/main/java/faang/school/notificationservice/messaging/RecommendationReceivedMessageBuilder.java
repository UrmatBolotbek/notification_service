package faang.school.notificationservice.messaging;

import faang.school.notificationservice.event.RecommendationReceivedEvent;

import java.util.Locale;

public class RecommendationReceivedMessageBuilder implements MessageBuilder<RecommendationReceivedEvent> {

    @Override
    public Class<?> getInstance() {
        return null;
    }

    @Override
    public String buildMessage(RecommendationReceivedEvent event, Locale locale) {
        return "";
    }

}
