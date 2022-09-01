package br.com.pagsys.msinventory.listener;

import br.com.pagsys.msinventory.dto.PurchaseDto;
import br.com.pagsys.msinventory.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PurchaseListener {

    @Autowired
    private InventoryService inventoryService;

    @KafkaListener(topics = "TRY-TO-PURCHASE", groupId = "groupId-1", containerFactory = "purchaseKafkaListenerContainerFactory")
    public void listenToLifecycleEvents(PurchaseDto purchase, @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String purchaseId){
        inventoryService.executePurchase(purchase);

    }


}
