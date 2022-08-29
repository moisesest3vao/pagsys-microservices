package br.com.pagsys.payment.controller;

import br.com.pagsys.payment.dto.OrderDto;
import br.com.pagsys.payment.repository.OrderRepository;
import br.com.pagsys.payment.service.PaymentService;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.SecurityContext;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public String ok(HttpServletRequest request){
        String authorization = request.getHeader("authorization");
        System.out.println(authorization);
        request.getHeaderNames().asIterator().forEachRemaining(h -> System.out.println(h));
        return "ok";
    }

    @GetMapping("/order")
    public ResponseEntity<?> create(OrderDto orderDto){
        OrderDto order = this.paymentService.create(orderDto);

        return order != null ? ResponseEntity.ok(order) : ResponseEntity.badRequest().build();
    }
}
