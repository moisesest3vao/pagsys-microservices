package br.com.pagsys.msemail.listener;

import br.com.pagsys.msemail.enums.EmailType;
import br.com.pagsys.msemail.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailListener {

    @Autowired
    EmailService emailService;

    @KafkaListener(topics = "USER-LIFECYCLE-EVENTS", groupId = "groupId-1")
    public void listenToLifecycleEvents(String type, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String email){
        log.info("Sending email to "+email+" of type "+type);
        System.out.println(type);
        switch (type) {
            case "JOINER" -> emailService.sendJoinerEmail(email);
            case "LEAVER" -> emailService.sendLeaverEmail(email);
        }
    }

    @KafkaListener(topics = "NEW-PURCHASE", groupId = "groupId-1")
    public void listenToNewPurchases(EmailType type, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String email){
        log.info("Sending email to "+email+" of type "+type.name());
        switch (type) {
            case SUCCESS_PURCHASE -> emailService.sendSuccessPurchase(email);
            case FAILED_PURCHASE -> emailService.sendFailedPurchase(email);
            case PROCESSING_PURCHASE -> emailService.sendProcessingPurchase(email);
        }
    }
}
