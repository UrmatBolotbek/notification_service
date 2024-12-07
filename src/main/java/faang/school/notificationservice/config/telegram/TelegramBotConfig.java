package faang.school.notificationservice.config.telegram;

import faang.school.notificationservice.tgbot.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;


@Configuration
public class TelegramBotConfig {
    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    public TelegramBotsLongPollingApplication tgBotApp(TelegramBot telegramBot) throws TelegramApiException{
        TelegramBotsLongPollingApplication botsApi = new TelegramBotsLongPollingApplication();
        botsApi.registerBot(botToken,telegramBot );
        return botsApi;
    }
    @Bean
    public TelegramClient telegramClient() {
        return new OkHttpTelegramClient(botToken);
    }

}
