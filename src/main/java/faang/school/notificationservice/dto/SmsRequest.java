package faang.school.notificationservice.dto;

import lombok.Data;

@Data
public class SmsRequest {
    private UserDto user;
    private String message;
}
