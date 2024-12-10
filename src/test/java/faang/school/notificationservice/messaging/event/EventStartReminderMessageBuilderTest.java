package faang.school.notificationservice.messaging.event;

import faang.school.notificationservice.dto.event.EventStartReminderEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class EventStartReminderMessageBuilderTest {

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private EventStartReminderMessageBuilder builder;

    @Test
    void testBuildMessage1Day() {
        EventStartReminderEvent event = EventStartReminderEvent.builder()
                .reminderType("1day")
                .build();

        when(messageSource.getMessage(eq("event.reminder.1day"), any(), any()))
                .thenReturn("Reminder: Event starts in 1 day!");

        String result = builder.buildMessage(event, Locale.getDefault());

        assertEquals("Reminder: Event starts in 1 day!", result);
    }

    @Test
    void testBuildMessage5Hours() {
        EventStartReminderEvent event = EventStartReminderEvent.builder()
                .reminderType("5hours")
                .build();

        when(messageSource.getMessage(eq("event.reminder.5hours"), any(), any()))
                .thenReturn("Reminder: Event starts in 5 hours!");

        String result = builder.buildMessage(event, Locale.getDefault());

        assertEquals("Reminder: Event starts in 5 hours!", result);
    }

    @Test
    void testBuildMessage1Hour() {
        EventStartReminderEvent event = EventStartReminderEvent.builder()
                .reminderType("1hour")
                .build();

        when(messageSource.getMessage(eq("event.reminder.1hour"), any(), any()))
                .thenReturn("Reminder: Event starts in 1 hour!");

        String result = builder.buildMessage(event, Locale.getDefault());

        assertEquals("Reminder: Event starts in 1 hour!", result);
    }

    @Test
    void testBuildMessage10Minutes() {
        EventStartReminderEvent event = EventStartReminderEvent.builder()
                .reminderType("10minutes")
                .build();

        when(messageSource.getMessage(eq("event.reminder.10minutes"), any(), any()))
                .thenReturn("Reminder: Event starts in 10 minutes!");

        String result = builder.buildMessage(event, Locale.getDefault());

        assertEquals("Reminder: Event starts in 10 minutes!", result);
    }

    @Test
    void testBuildMessageUnknownType() {
        EventStartReminderEvent event = EventStartReminderEvent.builder()
                .reminderType("unknown")
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> builder.buildMessage(event, Locale.getDefault()),
                "Expected IllegalArgumentException for unknown reminder type"
        );
    }
}
