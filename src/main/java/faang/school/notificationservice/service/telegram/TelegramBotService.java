package faang.school.notificationservice.service.telegram;

import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.service.NotificationService;
import faang.school.notificationservice.tgbot.TelegramBot;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
@AllArgsConstructor
public class TelegramBotService implements NotificationService, LongPollingSingleThreadUpdateConsumer {
    private final TelegramBot telegramBot;
    private final MessageSender messageSender;



    @Override
    public void send(UserDto user, String message) {
//        if (user.getId() == null) {
//            throw new IllegalArgumentException("User ID is required for Telegram notification");
//        }
//
//        SendMessage sendMessage = new SendMessage(message);
//        sendMessage.setChatId(user.getId());
//        sendMessage.setText(message);
//
//        try {
//            telegramBot.execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error sending message to Telegram", e);
//        }
    }
    @Override
    public UserDto.PreferredContact getPreferredContact() {
        return UserDto.PreferredContact.TELEGRAM;
    }

    @Override
    public void consume(List<Update> updates) {
        LongPollingSingleThreadUpdateConsumer.super.consume(updates);
    }

    @Override
    public void consume(Update update) {
        SendMessage message = null;
        if (message != null) {
            messageSender.sendMessage(message);
        }

    }
}
