package faang.school.notificationservice.service;

import com.vonage.client.VonageClient;
import com.vonage.client.sms.MessageStatus;
import com.vonage.client.sms.SmsSubmissionResponse;
import com.vonage.client.sms.messages.TextMessage;
import faang.school.notificationservice.dto.UserDto;
import faang.school.notificationservice.exception.SmsSendingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsService implements NotificationService {

    private final VonageClient vonageClient;

    @Autowired
    public SmsService(VonageClient vonageClient) {
        this.vonageClient = vonageClient;
    }

    @Override
    public void send(UserDto user, String message) {
        String phoneNumber = user.getPhone();
        String senderId = "Griffon";

        TextMessage messageToSend = new TextMessage(senderId, phoneNumber, message);

        try {
            SmsSubmissionResponse response = vonageClient.getSmsClient().submitMessage(messageToSend);

            if (response.getMessages().get(0).getStatus() == MessageStatus.OK) {
                System.out.println("Message sent successfully to " + phoneNumber);
            } else {
                System.out.println("Message failed with error: " +
                        response.getMessages().get(0).getErrorText());
            }
        } catch (Exception e) {
            log.error("Error while sending SMS to the user with id: {}", user.getId());
            throw new SmsSendingException("Error occurred while sending message", e);
        }
    }

    @Override
    public UserDto.PreferredContact getPreferredContact() {
        return null;
    }
}
