package faang.school.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentEvent {
    private Long postAuthorId;
    private Long commentAuthorId;
    private Long postId;
    private Long commentId;
    private String commentContent;
}