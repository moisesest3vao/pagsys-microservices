package br.com.pagsys.payment.repository;

import br.com.pagsys.payment.model.PurchaseRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseRequest, Long> {
}
