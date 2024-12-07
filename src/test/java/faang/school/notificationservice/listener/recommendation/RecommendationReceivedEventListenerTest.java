package faang.school.notificationservice.listener.recommendation;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.event.RecommendationReceivedEvent;
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
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecommendationReceivedEventListenerTest {

    private RecommendationReceivedEventListener listener;
    @Mock
    private ObjectMapper mapper;
    @Mock
    private MessageBuilder<RecommendationReceivedEvent> messageBuilder;
    @Mock
    private UserServiceClient userServiceClient;
    @Mock
    private SmsService notificationService;

    private Message message;
    private RecommendationReceivedEvent event;
    private UserDto user;
    private List<MessageBuilder<RecommendationReceivedEvent>> messageBuilders;
    private List<NotificationService> notificationServices;

    @BeforeEach
    @SuppressWarnings("unchecked")
    public void setUp() {
        messageBuilder = (MessageBuilder<RecommendationReceivedEvent>) mock(MessageBuilder.class);
        notificationService = mock(SmsService.class);

        messageBuilders = mock(List.class);
        notificationServices = mock(List.class);
        listener = new RecommendationReceivedEventListener(mapper, messageBuilders, userServiceClient, notificationServices);

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

        event = RecommendationReceivedEvent.builder()
                .recommendationId(15L)
                .receiverId(20L)
                .authorId(5L)
                .build();
        user = UserDto.builder()
                .preference(UserDto.PreferredContact.SMS)
                .username("Roma")
                .id(5L)
                .build();
    }

    @Test
    void testOnMessageSuccess() throws IOException {
        byte[] pattern = new byte[]{1,2,3,4};

        when(mapper.readValue(message.getBody(), RecommendationReceivedEvent.class)).thenReturn(event);
        when(userServiceClient.getUser(event.getReceiverId())).thenReturn(user);
        when(messageBuilder.getInstance()).thenAnswer(invocation -> RecommendationReceivedEvent.class);
        when(messageBuilder.buildMessage(eq(event), eq(Locale.UK))).thenReturn("You have received a recommendation from a user Roma");
        when(messageBuilders.stream()).thenReturn(Stream.of(messageBuilder));
        when(notificationServices.stream()).thenReturn(Stream.of(notificationService));
        when(notificationService.getPreferredContact()).thenReturn(UserDto.PreferredContact.SMS);
        assertDoesNotThrow(()-> listener.onMessage(message, pattern));

    }

}
