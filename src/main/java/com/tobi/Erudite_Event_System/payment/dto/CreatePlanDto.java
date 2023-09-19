package com.tobi.Erudite_Event_System.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePlanDto {


    @JsonProperty("name")
    private String name;


    @JsonProperty("interval")
    private String interval;


    @JsonProperty("amount")
    private Integer amount;
}

