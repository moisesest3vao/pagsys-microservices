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
    public ResponseEntity<?> create(@RequestBody PurchaseDto purchaseDto, HttpServletRequest request){

        String authorization = request.getHeader("authorization");
        PurchaseDto order = this.purchaseService.create(purchaseDto, authorization);

        if(order != null){
            //TODO: Remove from storage message
            //return 0 if the product is available and 1 if it isn't
            Integer response = this.purchaseService.sendInformationToInventory(order);
            return ResponseEntity.ok(order);
        }

        return ResponseEntity.badRequest().build();
    }
}
