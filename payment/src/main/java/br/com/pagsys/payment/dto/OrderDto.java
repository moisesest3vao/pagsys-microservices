package br.com.pagsys.payment.dto;

import br.com.pagsys.payment.model.Product;
import org.apache.catalina.User;

import java.math.BigDecimal;
import java.util.List;

public class OrderDto {
    private List<ProductDto> product;
    private Long amount;
    private BigDecimal totalPrice;
}
