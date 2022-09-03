package br.com.pagsys.msinventory.service;

import br.com.pagsys.msinventory.dto.ProductDto;
import br.com.pagsys.msinventory.dto.PurchaseDto;
import br.com.pagsys.msinventory.dto.PurchaseVerificationResultDto;
import br.com.pagsys.msinventory.enums.PurchaseVerificationResult;
import br.com.pagsys.msinventory.model.Product;
import br.com.pagsys.msinventory.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private KafkaTemplate<String, PurchaseVerificationResultDto> kafkaTemplate;

    public void executePurchase(PurchaseDto purchase) {
        final BigDecimal[] totalPrice = {new BigDecimal(0)};

        Map<String, Long> productsRepeatingCount = new HashMap<>();
        AtomicBoolean hasProductsUnavailable = new AtomicBoolean(false);
        purchase.getProducts().stream().forEach(System.out::println);
        purchase.getProducts().forEach(productId -> {
            Product product = productRepository.findById(productId).orElse(null);
            if(product == null || product.getAmount() <= 0){
                log.info("product was not found");
                hasProductsUnavailable.set(true);
            } else {
                Long count = productsRepeatingCount.get(productId);
                totalPrice[0] = totalPrice[0].add(product.getPrice());

                if(count == null){
                    productsRepeatingCount.put(productId, Long.parseLong("1"));
                } else {
                    productsRepeatingCount.put(productId, count+1);
                }
            }
        });

        PurchaseVerificationResultDto result =
                new PurchaseVerificationResultDto(
                        totalPrice[0],
                        purchase.getId()
                );

        if (!hasProductsUnavailable.get() && updateProductsAmount(productsRepeatingCount) == 0) {
            result.setResult(PurchaseVerificationResult.APPROVED);
            sendVerificationResponse(purchase.getId().toString(),result);
        } else {
            result.setResult(PurchaseVerificationResult.DENIED);
            sendVerificationResponse(purchase.getId().toString(), result);
        }
    }

    private Integer updateProductsAmount(Map<String, Long> productsRepeatingCount){

        List<Boolean> validations = new ArrayList<>();

        for (Map.Entry<String, Long> entry : productsRepeatingCount.entrySet()) {
            Product product = this.productRepository.findById(entry.getKey()).orElse(null);
            assert product != null;
            if(product.getAmount() < entry.getValue()){
                log.info("insufficient amount of products for this purchase");
               validations.add(false);
            } else {
                validations.add(true);
            }
        }

        if(validations.contains(false)){
            return 1;
        }else{
            for (Map.Entry<String, Long> entry : productsRepeatingCount.entrySet()) {
                Product product = this.productRepository.findById(entry.getKey()).orElse(null);
                assert product != null;
                product.setAmount(product.getAmount()-entry.getValue());
                this.productRepository.save(product);
            }
        }

        return 0;
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

    public Product update(String id, ProductDto dto) {
        Product product = this.productRepository.findById(id).orElse(null);
        if(product != null){
            product.update(dto);

            return this.productRepository.save(product);
        }
        return null;
    }

    public Integer delete(String id) {
        Product product = this.productRepository.findById(id).orElse(null);
        if(product != null){
            this.productRepository.delete(product);

            return 0;
        }
        return 1;
    }

    public Product getById(String id) {
        return this.productRepository.findById(id).orElse(null);
    }
}
