package br.com.pagsys.msemail.listener;

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

    @KafkaListener(topics = "USER-LIFECYCLE-EVENTS", groupId = "groupId-1" )
    public void listen(String type, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String email){
        log.info("Sending email to "+email+" of type "+type);
        switch (type) {
            case "JOINER" -> emailService.sendJoinerEmail(email);
            case "LEAVER" -> emailService.sendLeaverEmail(email);
        }
    }
}
