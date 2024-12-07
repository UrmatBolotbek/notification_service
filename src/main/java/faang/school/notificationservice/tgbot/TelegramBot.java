package faang.school.notificationservice.tgbot;

import lombok.AllArgsConstructor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot implements LongPollingUpdateConsumer {
    private final String token;


    @Override
    public String getBotToken() {
        return token;
    }


    @Override
    public void consume(List<Update> list) {

    }

    public void execute(SendMessage sendMessage) {
    }
}
