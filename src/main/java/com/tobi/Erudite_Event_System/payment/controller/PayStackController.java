package com.tobi.Erudite_Event_System.payment.controller;

import com.tobi.Erudite_Event_System.payment.dto.*;
import com.tobi.Erudite_Event_System.payment.service.PayStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//@RestController
//@RequestMapping("api/payment")
@RequiredArgsConstructor
public class PayStackController {

    private final PayStackService paystackService;


    @PostMapping("/initializePayment")
    @ResponseStatus(HttpStatus.OK)
    public InitializePaymentResponse initializePayment(@Validated @RequestBody InitializePaymentDto initializePaymentDto) throws Throwable {
        return paystackService.initializePayment(initializePaymentDto);
    }


}

