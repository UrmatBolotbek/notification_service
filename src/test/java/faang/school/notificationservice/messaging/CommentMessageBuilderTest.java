package faang.school.notificationservice.messaging;

import faang.school.notificationservice.client.UserServiceClient;
import faang.school.notificationservice.dto.CommentEvent;
import faang.school.notificationservice.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommentMessageBuilderTest {

    @Mock
    private MessageSource messageSource;

    @Mock
    private UserServiceClient userServiceClient;

    @InjectMocks
    private CommentMessageBuilder commentMessageBuilder;

    private CommentEvent commentEvent;
    private static final Long POST_AUTHOR_ID = 1L;
    private static final Long COMMENT_AUTHOR_ID = 2L;
    private static final Long POST_ID = 3L;
    private static final Long COMMENT_ID = 4L;
    private static final String COMMENT_CONTENT = "This is a comment";
    private static final String COMMENT_AUTHOR_NAME = "JohnDoe";

    @BeforeEach
    void setUp() {
        commentEvent = new CommentEvent(POST_AUTHOR_ID, COMMENT_AUTHOR_ID, POST_ID, COMMENT_ID, COMMENT_CONTENT);
    }

    @Test
    void testBuildMessage() {
        Locale locale = Locale.getDefault();

        UserDto userDto = new UserDto();
        userDto.setId(commentEvent.getCommentAuthorId());
        userDto.setUsername(COMMENT_AUTHOR_NAME);

        Mockito.when(userServiceClient.getUser(commentEvent.getCommentAuthorId()))
                .thenReturn(userDto);

        Mockito.when(messageSource.getMessage("comment.new", new Object[]{COMMENT_AUTHOR_NAME}, locale))
                .thenReturn("User JohnDoe left a comment on your post");

        String message = commentMessageBuilder.buildMessage(commentEvent, locale);

        assertEquals("User JohnDoe left a comment on your post", message);
        Mockito.verify(userServiceClient).getUser(commentEvent.getCommentAuthorId());
        Mockito.verify(messageSource).getMessage("comment.new", new Object[]{COMMENT_AUTHOR_NAME}, locale);
    }

    @Test
    void testSupportsEventType() {
        Class<?> supportedType = commentMessageBuilder.getInstance();
        assertEquals(CommentEvent.class, supportedType);
    }
}