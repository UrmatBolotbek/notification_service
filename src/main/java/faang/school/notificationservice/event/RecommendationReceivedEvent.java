package faang.school.notificationservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class RecommendationReceivedEvent {

    private long authorId;
    private long receiverId;
    private long recommendationId;

}
