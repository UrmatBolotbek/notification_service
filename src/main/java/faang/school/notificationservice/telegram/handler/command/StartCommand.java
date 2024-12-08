package faang.school.notificationservice.telegram.handler.command;

import faang.school.notificationservice.dto.CommandDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j

@Component(value = "/start")
public class StartCommand extends Command {
    public StartCommand(Environment environment) {
        super(environment);
    }

    @Override
    public SendMessage execute(CommandDto commandDto) {
        String greetings = String.format(
                environment.getProperty("telegram.greetings", "Hello, %s!"),
                commandDto.getFirstName()
        );
        return createMessage(greetings, commandDto.getChatId(), commandDto.getFirstName());
    }

    private SendMessage createMessage(String messageText, long chatId, String firstName) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(String.format(messageText, firstName));
        return sendMessage;
    }
}
