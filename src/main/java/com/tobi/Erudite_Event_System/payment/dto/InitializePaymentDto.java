package com.tobi.Erudite_Event_System.payment.dto;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InitializePaymentDto {

    private BigDecimal amount;
    private String email;

}

