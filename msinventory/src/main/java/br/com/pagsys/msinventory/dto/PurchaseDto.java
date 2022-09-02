package br.com.pagsys.msinventory.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class PurchaseDto {

    private Long id;
    private List<String> products;
    private String user;

}

