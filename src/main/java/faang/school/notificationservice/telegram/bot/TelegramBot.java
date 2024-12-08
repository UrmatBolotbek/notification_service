package faang.school.notificationservice.telegram.bot;

import faang.school.notificationservice.config.telegram.TelegramConfig;
import faang.school.notificationservice.telegram.handler.command.CommandExecutor;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Component
@RequiredArgsConstructor
@Slf4j
@Service
public class TelegramBot extends TelegramLongPollingBot {

    private final CommandExecutor commandExecutor;


    private static final char COMMAND_ANNOUNCE ='/';
    private final TelegramConfig config;

    @PostConstruct
    public void init() {
        log.info("Initializing Telegram Bot with name: {} and token: {}", config.getName(), config.getToken());

        log.info("Bot Name: {}", config.getName());
        log.info("Bot Token: {}", config.getToken());

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            log.error("Error registering bot", e);
        }
    }

    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String firstName = update.getMessage().getFrom().getFirstName();

            if (checkMessage(messageText)) {
                SendMessage sendMessage = commandExecutor.executeCommand(chatId, firstName, messageText);
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
    private boolean checkMessage(String messageText) {
        return COMMAND_ANNOUNCE == messageText.charAt(0);
    }
}
