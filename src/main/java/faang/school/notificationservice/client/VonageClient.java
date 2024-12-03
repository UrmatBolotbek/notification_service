package faang.school.notificationservice.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VonageClient {
    @Value("${vonage.api.key}")
    private String key;
    @Value("${vonage.api.secret}")
    private String secret;

    @Bean
    public com.vonage.client.VonageClient createVonageClient() {
        return com.vonage.client.VonageClient.builder()
                .apiKey(key)
                .apiSecret(secret)
                .build();
    }
}
