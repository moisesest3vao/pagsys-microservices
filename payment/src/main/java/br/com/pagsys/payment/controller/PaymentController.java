package br.com.pagsys.payment.controller;

import br.com.pagsys.payment.dto.OrderDto;
import br.com.pagsys.payment.repository.OrderRepository;
import br.com.pagsys.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/order")
    public ResponseEntity<?> create(OrderDto orderDto){

        OrderDto order = this.paymentService.create(orderDto);

        return order!= null ? ResponseEntity.ok(order) : ResponseEntity.badRequest().build();
    }
}
