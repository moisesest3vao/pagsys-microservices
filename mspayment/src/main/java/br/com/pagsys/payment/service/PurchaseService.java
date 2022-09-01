package br.com.pagsys.payment.service;

import br.com.pagsys.payment.clients.UserClient;
import br.com.pagsys.payment.dto.PurchaseDto;
import br.com.pagsys.payment.dto.UserDto;
import br.com.pagsys.payment.enums.EmailType;
import br.com.pagsys.payment.model.Purchase;
import br.com.pagsys.payment.repository.PurchaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@Slf4j
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private UserClient userClient;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, PurchaseDto> kafkaTemplatePurchase;


    public PurchaseService() {
    }

    public PurchaseDto create(PurchaseDto dto, String authorization){
        //calling user microservice
        UserDto userByToken = userClient.getUserByToken(authorization);


        Purchase purchase = Purchase.build(dto, userByToken);
        Purchase save = purchaseRepository.save(purchase);


        kafkaTemplate.send("NEW-PURCHASE", userByToken.getEmail(), EmailType.PROCESSING_PURCHASE.value);

        return new PurchaseDto(save);
    }


    public Integer sendInformationToInventory(PurchaseDto order) {
        log.info("Trying to send purchase to Kafka");
        kafkaTemplatePurchase.send("TRY-TO-PURCHASE", order.getId().toString(), order);
        return 0;
    }

    public void validate(String purchaseId, String validationResult) {
        Purchase purchase = purchaseRepository.findById(Long.parseLong(purchaseId)).orElse(null);
        if(purchase != null){
            UserDto userById = userClient.getUserBySub(purchase.getUser());

            switch (validationResult){
                case "DENIED" -> {
                    log.info("NOTIFICATIONS"+ userById.getEmail() +EmailType.FAILED_PURCHASE.value);
                    kafkaTemplate.send("NEW-PURCHASE", userById.getEmail() ,EmailType.FAILED_PURCHASE.value);
                }
                case "APPROVED" -> {
                    log.info("NOTIFICATIONS"+ userById.getEmail() +EmailType.SUCCESS_PURCHASE.value);
                    kafkaTemplate.send("NEW-PURCHASE", userById.getEmail(), EmailType.SUCCESS_PURCHASE.value);
                }
            }
        }
    }
}
