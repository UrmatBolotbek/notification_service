package faang.school.notificationservice.service.telegram;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
@Service
@AllArgsConstructor
public class MessageSender {
    private final TelegramClient telegramClient;

    public void sendMessage(SendMessage sendMessage){
        try {
            telegramClient.execute(sendMessage);
        }catch (TelegramApiException e){
            throw new RuntimeException(e);
        }
    }
}
