package faang.school.notificationservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProjectFollowerEvent {
    private Long subscriberId;
    private Long projectId;
    private Long creatorId;

}
