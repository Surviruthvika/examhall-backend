package com.anurag.examhall.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsService {

    @Value("${twilio.account.sid:}")
    private String accountSid;

    @Value("${twilio.auth.token:}")
    private String authToken;

    @Value("${twilio.phone.number:}")
    private String fromPhone;

    private boolean twilioEnabled = false;

    @PostConstruct
    public void init() {
        if (accountSid != null && !accountSid.isEmpty()
                && authToken != null && !authToken.isEmpty()) {
            try {
                Twilio.init(accountSid, authToken);
                twilioEnabled = true;
                log.info("Twilio SMS service initialized successfully.");
            } catch (Exception e) {
                log.warn("Twilio initialization failed: {}. SMS will be disabled.", e.getMessage());
            }
        } else {
            log.warn("Twilio credentials not configured. SMS notifications will be skipped.");
        }
    }

    public boolean sendSms(String toPhone, String messageBody) {
        if (!twilioEnabled) {
            log.info("[SMS SKIPPED - Twilio not configured] To: {} | Message: {}", toPhone, messageBody);
            return false;
        }

        try {
            // Ensure phone starts with +
            String formattedPhone = toPhone.startsWith("+") ? toPhone : "+91" + toPhone;

            Message message = Message.creator(
                    new PhoneNumber(formattedPhone),
                    new PhoneNumber(fromPhone),
                    messageBody
            ).create();

            log.info("SMS sent successfully. SID: {}", message.getSid());
            return true;
        } catch (Exception e) {
            log.error("Failed to send SMS to {}: {}", toPhone, e.getMessage());
            return false;
        }
    }

    public boolean isTwilioEnabled() {
        return twilioEnabled;
    }
}
