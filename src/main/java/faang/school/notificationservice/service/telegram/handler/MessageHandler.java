package faang.school.notificationservice.service.telegram.handler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface MessageHandler {
    SendMessage handler(Long chatId);
}
