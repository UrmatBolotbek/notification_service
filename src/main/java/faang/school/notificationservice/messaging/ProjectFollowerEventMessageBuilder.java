package faang.school.notificationservice.messaging;

import faang.school.notificationservice.dto.event.ProjectFollowerEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ProjectFollowerEventMessageBuilder implements MessageBuilder<ProjectFollowerEvent> {
    private final MessageSource messageSource;

    @Override
    public String buildMessage(ProjectFollowerEvent event, Locale locale) {

        return messageSource.getMessage("projectFollower.new", new Object[]{event.getSubscriberId(), event.getProjectId()}, locale);
    }

    @Override
    public Class<?> getInstance() {
        return ProjectFollowerEventMessageBuilder.class;
    }
}
