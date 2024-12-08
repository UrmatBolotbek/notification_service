package faang.school.notificationservice.config.redis;

import faang.school.notificationservice.listener.event.EventStartEventListener;
import faang.school.notificationservice.listener.event.EventStartReminderEventListener;
import faang.school.notificationservice.listener.CommentEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    private final EventStartEventListener eventStartEventListener;
    private final EventStartReminderEventListener eventStartReminderEventListener;

    private final CommentEventListener commentEventListener;

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;
    @Value("${spring.data.redis.channels.comment-channel}")
    private String topicComment;

    @Value("${spring.data.redis.channels.event-start-event-channel}")
    private String eventStartEventTopic;

    @Value("${spring.data.redis.channels.event-start-reminder-event-channel}")
    private String eventStartReminderEventTopic;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        return new JedisConnectionFactory(redisConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer() {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());

        addMessageListenerInContainer(eventStartEventListener, new ChannelTopic(eventStartEventTopic), container);
        addMessageListenerInContainer(eventStartEventListener, eventStartEventTopic, container);
        addMessageListenerInContainer(eventStartReminderEventListener, eventStartReminderEventTopic, container);

        addMessageListenerInContainer(commentEventListener, new ChannelTopic(topicComment), container);
        return container;
    }

    private void addMessageListenerInContainer(MessageListener listenerAdapter, ChannelTopic topic, RedisMessageListenerContainer container) {
        listenerAdapter = new MessageListenerAdapter(listenerAdapter);
        container.addMessageListener(listenerAdapter, topic);
    }
}