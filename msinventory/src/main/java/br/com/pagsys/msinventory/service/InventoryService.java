package br.com.pagsys.msinventory.service;

import br.com.pagsys.msinventory.dto.ProductDto;
import br.com.pagsys.msinventory.dto.PurchaseDto;
import br.com.pagsys.msinventory.dto.PurchaseVerificationResultDto;
import br.com.pagsys.msinventory.enums.PurchaseVerificationResult;
import br.com.pagsys.msinventory.model.Product;
import br.com.pagsys.msinventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private KafkaTemplate<String, PurchaseVerificationResultDto> kafkaTemplate;

    public void executePurchase(PurchaseDto purchase) {
        BigDecimal totalPrice = new BigDecimal(0);

        boolean areProductsAvailable = purchase.getProducts().stream().noneMatch(productId -> {
            Product product = productRepository.findById(Long.parseLong(productId)).orElse(null);
            if (product == null || product.getAmount() <= 0) {
                return true;
            }
            product.setAmount(product.getAmount()-1);
            productRepository.save(product);
            return false;
        });

        PurchaseVerificationResultDto result =
                new PurchaseVerificationResultDto(
                        totalPrice,
                        purchase.getId()
                );

        if (areProductsAvailable) {
            result.setResult(PurchaseVerificationResult.APPROVED);
            sendVerificationResponse(purchase.getId().toString(),result);
        } else {
            result.setResult(PurchaseVerificationResult.DENIED);
            sendVerificationResponse(purchase.getId().toString(), result);
        }
    }

    private void sendVerificationResponse(String id, PurchaseVerificationResultDto result) {
        kafkaTemplate.send("PURCHASE-VERIFICATION", id, result);
    }


    public Page<Product> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product create(ProductDto dto) {
        Product product = new Product(dto);
        return this.productRepository.save(product);
    }

    public Product update(Long id, ProductDto dto) {
        Product product = this.productRepository.findById(id).orElse(null);
        if(product != null){
            product.update(dto);

            return this.productRepository.save(product);
        }
        return null;
    }

    public Integer delete(Long id) {
        Product product = this.productRepository.findById(id).orElse(null);
        if(product != null){
            this.productRepository.delete(product);

            return 0;
        }
        return 1;
    }
}
