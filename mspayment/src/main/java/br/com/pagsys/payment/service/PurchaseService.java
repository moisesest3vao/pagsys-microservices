package br.com.pagsys.payment.service;

import br.com.pagsys.payment.dto.PurchaseDto;
import br.com.pagsys.payment.dto.PurchaseVerificationResultDto;
import br.com.pagsys.payment.enums.EmailType;
import br.com.pagsys.payment.enums.PurchaseStatus;
import br.com.pagsys.payment.model.Purchase;
import br.com.pagsys.payment.repository.PurchaseRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PurchaseService {
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private KafkaTemplate<String, PurchaseDto> kafkaTemplatePurchase;

    public PurchaseDto create( PurchaseDto dto){
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(jwt != null){
            String userId = jwt.getClaims().get("sub").toString();
            String userEmail = jwt.getClaims().get("email").toString();
            System.out.println(userEmail);
            System.out.println(userId);

            Purchase purchase = Purchase.build(dto, userId, userEmail);
            Purchase save = purchaseRepository.save(purchase);

            kafkaTemplate.send("NEW-PURCHASE", userEmail, EmailType.PROCESSING_PURCHASE.value);

            return new PurchaseDto(save);
        }
        return null;
    }

    public void sendInformationToInventory(PurchaseDto order) {
        log.info("sending purchase to inventory microsservice");
        kafkaTemplatePurchase.send("TRY-TO-PURCHASE", order.getId().toString(), order);
    }

    public void validate(String purchaseId, PurchaseVerificationResultDto validationResult) {
        Purchase purchase = purchaseRepository.findById(Long.parseLong(purchaseId)).orElse(null);
        if(purchase != null){
            try{
                String userEmail = purchase.getUserEmail();

                switch (validationResult.getResult().toString()){
                    case "DENIED" -> {
                        log.info("failed purchase verification for "+userEmail);
                        doErrorTreatment(purchase);
                        kafkaTemplate.send("NEW-PURCHASE", userEmail ,EmailType.FAILED_PURCHASE.value);
                    }
                    case "APPROVED" -> {
                        log.info("success purchase verification for "+userEmail);

                        doSuccessTreatment(purchase);
                        kafkaTemplate.send("NEW-PURCHASE", userEmail, EmailType.SUCCESS_PURCHASE.value);
                    }
                }
            }catch (FeignException.FeignClientException e){
                log.error("an error occurred while calling the users microservice");
                doErrorTreatment(purchase);
            }
        }
    }

    public void doErrorTreatment(Purchase purchase) {
        purchase.setStatus(PurchaseStatus.ERROR);
        this.purchaseRepository.save(purchase);
    }
    public void doSuccessTreatment(Purchase purchase){
        purchase.setStatus(PurchaseStatus.FINISHED);
        this.purchaseRepository.save(purchase);
    }
}
