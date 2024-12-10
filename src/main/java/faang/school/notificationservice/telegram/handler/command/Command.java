package faang.school.notificationservice.telegram.handler.command;

import faang.school.notificationservice.dto.CommandDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@RequiredArgsConstructor
@PropertySource("classpath:messagetg.properties")
public abstract class Command {
    protected final Environment environment;

    public abstract SendMessage execute(CommandDto commandDto);
}
