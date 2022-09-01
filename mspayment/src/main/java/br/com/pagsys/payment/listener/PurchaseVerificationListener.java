package br.com.pagsys.payment.listener;

import br.com.pagsys.payment.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PurchaseVerificationListener {

    @Autowired
    private PurchaseService purchaseService;

    @KafkaListener(topics = "PURCHASE-VERIFICATION", groupId = "groupId-1")
    void listenToLifecycleEvents(String validationResult, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String purchaseId){
        purchaseService.validate(purchaseId, validationResult);
    }
}
