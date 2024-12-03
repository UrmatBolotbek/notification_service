package faang.school.notificationservice.controller;

import faang.school.notificationservice.dto.SmsRequest;
import faang.school.notificationservice.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestBody SmsRequest smsRequest) {
        try {
            smsService.send(smsRequest.getUser(), smsRequest.getMessage());
            return ResponseEntity.ok("SMS sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to send SMS: " + e.getMessage());
        }
    }
}