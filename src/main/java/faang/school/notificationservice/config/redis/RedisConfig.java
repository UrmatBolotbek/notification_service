package faang.school.notificationservice.config.redis;

import faang.school.notificationservice.dto.event.EventStartReminderEvent;
import faang.school.notificationservice.listener.event.EventStartEventListener;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;

    @Value("${spring.data.redis.channels.event-start-event-channel.name}")
    private String eventStartEventTopic;

    @Value("${spring.data.redis.channels.event-start-reminder-event-channel.name}")
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
    RedisMessageListenerContainer redisContainer(MessageListenerAdapter eventStartListener,
                                                 MessageListenerAdapter eventStartReminderEvent) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(eventStartListener, eventStartEventTopic());
        container.addMessageListener(eventStartReminderEvent, eventStartReminderEventTopic());
        return container;
    }

    @Bean
    MessageListenerAdapter eventStartListener(EventStartEventListener eventStartEventListener) {
        return new MessageListenerAdapter(eventStartEventListener);
    }

    @Bean
    MessageListenerAdapter eventStartReminderListener(EventStartReminderEvent eventStartReminderEvent) {
        return new MessageListenerAdapter(eventStartReminderEvent);
    }

    @Bean
    ChannelTopic eventStartEventTopic() {
        return new ChannelTopic(eventStartEventTopic);
    }

    @Bean
    ChannelTopic eventStartReminderEventTopic() {
        return new ChannelTopic(eventStartReminderEventTopic);
    }
}
