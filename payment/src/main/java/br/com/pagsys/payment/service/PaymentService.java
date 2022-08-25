package br.com.pagsys.payment.service;

import br.com.pagsys.payment.dto.OrderDto;
import br.com.pagsys.payment.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    @Autowired
    private OrderRepository orderRepository;

    public OrderDto create(OrderDto dto){
        //TODO: Initialize the workflow

        return null;
    }
}
