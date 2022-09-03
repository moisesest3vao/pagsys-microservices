package br.com.pagsys.payment.controller;

import br.com.pagsys.payment.dto.PurchaseDto;
import br.com.pagsys.payment.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PurchaseService purchaseService;

    @GetMapping
    public String ok(HttpServletRequest request){
        return "ok";
    }

    @GetMapping("/order")
    public ResponseEntity<?> create(@RequestBody @Valid PurchaseDto purchaseDto){

        PurchaseDto order = this.purchaseService.create(purchaseDto);

        if(order != null){
            this.purchaseService.sendInformationToInventory(order);
            return ResponseEntity.ok(order);
        }

        return ResponseEntity.badRequest().build();
    }
}
