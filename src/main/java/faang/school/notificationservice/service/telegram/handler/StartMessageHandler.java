package faang.school.notificationservice.service.telegram.handler;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
@Service
public class StartMessageHandler implements MessageHandler{
    private static final String START_MESSAGE = """
            Welcome to our bot!
            """;
    @Override
    public SendMessage handler(Long chatId) {
        return SendMessage
                .builder()
                .chatId(chatId)
                .text(START_MESSAGE)
                .build();
    }
}
