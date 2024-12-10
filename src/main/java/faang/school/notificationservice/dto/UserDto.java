package faang.school.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    @JsonIgnore(value = false)
    private long telegramChatId;
    private long id;
    private String username;
    private String email;
    private String phone;
    private PreferredContact preference;
    private String locale;

    public enum PreferredContact {
        EMAIL, SMS, TELEGRAM
    }
}
