package faang.school.notificationservice.listener.goal;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.dto.goal.GoalCompletedEvent;
import faang.school.notificationservice.messaging.MessageBuilder;
import faang.school.notificationservice.service.NotificationService;
import faang.school.notificationservice.service.SmsService;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.connection.Message;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoalCompletedEventListenerTest {

    private GoalCompletedEventListener listener;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private MessageBuilder<GoalCompletedEvent> messageBuilder;

    @Mock
    private UserServiceClient userServiceClient;

    @Mock
    private SmsService notificationService;

    private Message message;
    private GoalCompletedEvent event;
    private UserDto user;
    private List<MessageBuilder<GoalCompletedEvent>> messageBuilders;
    private List<NotificationService> notificationServices;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        messageBuilder = (MessageBuilder<GoalCompletedEvent>) mock(MessageBuilder.class);
        notificationService = mock(SmsService.class);

        messageBuilders = mock(List.class);
        notificationServices = mock(List.class);
        listener = new GoalCompletedEventListener(messageBuilders, mapper, userServiceClient, notificationServices);

        message = new Message() {
            @Override
            public byte @NotNull [] getBody() {
                return new byte[15];
            }

            @Override
            public byte @NotNull [] getChannel() {
                return new byte[0];
            }
        };

        event = GoalCompletedEvent.builder()
                .goalId(15L)
                .userId(21L)
                .completedAt(LocalDateTime.now())
                .build();
        user = UserDto.builder()
                .preference(UserDto.PreferredContact.SMS)
                .username("Robert")
                .id(21L)
                .build();
    }

    @Test
    void testOnMessageSuccess() throws IOException {
        byte[] pattern = new byte[]{1, 2, 3, 4};

        when(mapper.readValue(message.getBody(), GoalCompletedEvent.class)).thenReturn(event);
        when(userServiceClient.getUser(event.getUserId())).thenReturn(user);
        when(messageBuilder.getInstance()).thenAnswer(invocation -> GoalCompletedEvent.class);
        when(messageBuilder.buildMessage(eq(event), eq(Locale.UK))).thenReturn("Congrats, Robert! You completed a goal!");
        when(messageBuilders.stream()).thenReturn(Stream.of(messageBuilder));
        when(notificationServices.stream()).thenReturn(Stream.of(notificationService));
        when(notificationService.getPreferredContact()).thenReturn(UserDto.PreferredContact.SMS);
        assertDoesNotThrow(() -> listener.onMessage(message, pattern));
    }
}