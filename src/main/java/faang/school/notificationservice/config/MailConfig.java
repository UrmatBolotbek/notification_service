package faang.school.notificationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String mailHost;

    @Value("${spring.mail.port}")
    private int mailPort;

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;

    @Value("${spring.mail.properties.connectiontimeout}")
    private int connectionTimeout;

    @Value("${spring.mail.properties.writetimeout}")
    private int writeTimeout;

    @Value("${spring.mail.properties.ssl}")
    private boolean isSslOn;

    @Value("${spring.mail.properties.auth}")
    private boolean isAuthOn;




    @Bean
    public JavaMailSender mailSender()  {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailHost);
        mailSender.setPort(mailPort);

        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", isAuthOn);
        props.put("mail.smtp.ssl.enable", isSslOn);
        props.put("mail.smtp.connectiontimeout", connectionTimeout);
        props.put("mail.smtp.timeout", writeTimeout);

        return mailSender;
    }
}
