package faang.school.notificationservice.service;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.SmsSubmissionResponseMessage;
import com.vonage.client.sms.messages.TextMessage;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.exception.SmsSendingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService implements NotificationService {

    private final VonageClient vonageClient;
    @Value("${vonage.from}")
    private String sendingPhoneNumber;

    @Override
    public void send(UserDto user, String text) {
        TextMessage message = new TextMessage(
                sendingPhoneNumber,
                user.getPhone(),
                text
        );
        SmsSubmissionResponse response = vonageClient.getSmsClient().submitMessage(message);
        SmsSubmissionResponseMessage responseMessage = response.getMessages().get(0);

        if (responseMessage.getStatus() == MessageStatus.OK) {
            log.info("Sms for user {} sent successfully", user.getId());
        } else {
            log.error("Message failed with error: {}", responseMessage.getErrorText());
            throw new SmsSendingException("Sms for user %s failed. Error: %s"
                    .formatted(user.getId(), responseMessage.getErrorText()));
        }
    }

    @Override
    public UserDto.PreferredContact getPreferredContact() {
        return UserDto.PreferredContact.SMS;
    }
}