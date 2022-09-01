package br.com.pagsys.msinventory.service;

import br.com.pagsys.msinventory.dto.PurchaseDto;
import br.com.pagsys.msinventory.enums.PurchaseVerificationResult;
import br.com.pagsys.msinventory.model.Product;
import br.com.pagsys.msinventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void executePurchase(PurchaseDto purchase) {
        boolean areProductsAvailable = purchase.getProducts().stream().noneMatch(productId -> {
            Product product = productRepository.findById(Long.parseLong(productId)).orElse(null);
            if (product == null || product.getAmount() < 0) {
                return true;
            }
            product.setAmount(product.getAmount()-1);
            productRepository.save(product);
            return false;
        });

        if (areProductsAvailable) {
            sendVerificationResponse(purchase.getId(), PurchaseVerificationResult.APPROVED);
        } else {
            sendVerificationResponse(purchase.getId(), PurchaseVerificationResult.DENIED);
        }
    }

    private void sendVerificationResponse(Long id, PurchaseVerificationResult result) {
        kafkaTemplate.send("PURCHASE-VERIFICATION", id.toString(), result.value);
    }



}
