package faang.school.notificationservice.telegram.handler.command;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public enum CommandName {
    START("/start"),
    ERROR("/error"),
    HELP("/help");
    private final String commandName;
}
