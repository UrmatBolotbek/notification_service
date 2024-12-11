package faang.school.notificationservice.event;

import lombok.Data;

@Data
public class AchievementEvent {

    private long userId;
    private long achievementId;
    private String achievementName;
    private String achievementDescription;

}
