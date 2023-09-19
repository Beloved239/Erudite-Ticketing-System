package com.tobi.Erudite_Event_System.payment.service;


import com.tobi.Erudite_Event_System.payment.dto.*;

public interface PayStackService {
    InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto);
    PaymentVerificationResponse paymentVerification(String reference) throws Exception;
}

