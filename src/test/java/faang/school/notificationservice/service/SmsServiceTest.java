package faang.school.notificationservice.service;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsClient;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.SmsSubmissionResponseMessage;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.exception.SmsSendingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmsServiceTest {

    @Mock
    private VonageClient vonageClient;
    @Mock
    private SmsClient smsClient;
    @Mock
    private SmsSubmissionResponse response;
    @Mock
    private SmsSubmissionResponseMessage responseMessage;

    private SmsService smsService;

    private static final String PHONE_NUMBER_WE_USE_TO_SEND_MESSAGES = "18335642316"; //We really use it
    private static final Long USER_ID = 1L;
    private static final String USER_PHONE_NUMBER = "123456789";
    private static final String MESSAGE = "Sample message";

    @BeforeEach
    void setUp() {
        when(vonageClient.getSmsClient()).thenReturn(smsClient);
        smsService = new SmsService(vonageClient);

        ReflectionTestUtils.setField(smsService, "sendingPhoneNumber", PHONE_NUMBER_WE_USE_TO_SEND_MESSAGES);
    }

    @Test
    void send_SuccessfulMessageSubmission() {
        UserDto user = UserDto.builder()
                .id(USER_ID)
                .phone(USER_PHONE_NUMBER)
                .build();

        when(smsClient.submitMessage(any())).thenReturn(response);
        when(response.getMessages()).thenReturn(List.of(responseMessage));
        when(responseMessage.getStatus()).thenReturn(MessageStatus.OK);

        assertDoesNotThrow(() -> smsService.send(user, MESSAGE));

        verify(vonageClient.getSmsClient(), times(1)).submitMessage(any());
    }

    @Test
    void send_MessageSubmissionFails() {
        UserDto user = UserDto.builder()
                .id(USER_ID)
                .phone(USER_PHONE_NUMBER)
                .build();

        when(smsClient.submitMessage(any())).thenReturn(response);
        when(response.getMessages()).thenReturn(List.of(responseMessage));
        when(responseMessage.getStatus()).thenReturn(MessageStatus.INTERNAL_ERROR);
        when(responseMessage.getErrorText()).thenReturn("Some error occurred");

        SmsSendingException exception = assertThrows(SmsSendingException.class, () -> smsService.send(user, MESSAGE));
        assertEquals("Sms for user 1 failed. Error: Some error occurred", exception.getMessage());

        verify(vonageClient.getSmsClient(), times(1)).submitMessage(any());
    }
}