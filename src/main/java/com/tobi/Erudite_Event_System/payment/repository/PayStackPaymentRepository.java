package com.tobi.Erudite_Event_System.payment.repository;


import com.tobi.Erudite_Event_System.payment.entity.PaymentPayStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayStackPaymentRepository extends JpaRepository<PaymentPayStack, Long> {
    PaymentPayStack findByReference(String reference);
}

