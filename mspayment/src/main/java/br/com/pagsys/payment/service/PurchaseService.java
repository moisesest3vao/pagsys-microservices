package br.com.pagsys.payment.service;

import br.com.pagsys.payment.clients.UserClient;
import br.com.pagsys.payment.dto.PurchaseDto;
import br.com.pagsys.payment.dto.UserDto;
import br.com.pagsys.payment.enums.EmailType;
import br.com.pagsys.payment.model.Purchase;
import br.com.pagsys.payment.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private UserClient userClient;

    public PurchaseService() {
    }

    public PurchaseDto create(PurchaseDto dto, String authorization){
        UserDto userByToken = userClient.getUserByToken(authorization);

        Purchase purchase = Purchase.build(dto, userByToken);
        System.out.println(purchase);
        Purchase save = purchaseRepository.save(purchase);
        System.out.println(save);

        kafkaTemplate.send("NEW-PURCHASE", userByToken.getEmail(), EmailType.SUCCESS_PURCHASE.value);

        return new PurchaseDto(save);
    }


}
