package faang.school.notificationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients("faang.school.notificationservice.client")
public class NotificationServiceApp {
    public static void main(String[] args) {
        new SpringApplicationBuilder(NotificationServiceApp.class)
                .bannerMode(Banner.Mode.OFF)
                .run(args);

//        Для проверки доставки смс(3 строчки выше нужно закоментить при проверке)
//        UserDto user = new UserDto();
//        user.setPhone("+4917670217542");
//        user.setEmail("a@gmail.com");
//        user.setPreference(UserDto.PreferredContact.SMS);
//
//        ConfigurableApplicationContext context = SpringApplication.run(NotificationServiceApp.class, args);
//        SmsService bean = context.getBean(SmsService.class);
//        bean.send(user, "Hello from griffon");
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
