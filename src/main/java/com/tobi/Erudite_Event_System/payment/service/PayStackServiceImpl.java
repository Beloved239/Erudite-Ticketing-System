package com.tobi.Erudite_Event_System.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobi.Erudite_Event_System.payment.dto.*;
import com.tobi.Erudite_Event_System.payment.entity.PaymentPayStack;
import com.tobi.Erudite_Event_System.payment.repository.PayStackPaymentRepository;
import com.tobi.Erudite_Event_System.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;



@Service
@RequiredArgsConstructor
public class PayStackServiceImpl implements PayStackService {

    private final PayStackPaymentRepository paystackPaymentRepository;
    private final WebClient webClient;

    @Value("${applyforme.paystack.public.key}")
    private String payStackSecretKey;

    @Override
    public InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto) {

        InitializePaymentResponse response = webClient.post()
                .uri(ResponseUtils.PAYSTACK_INITIALIZE_PAY)
                .header("Content-type", "application/json")
                .header("Authorization", "Bearer " + payStackSecretKey)
                .body(BodyInserters.fromValue(initializePaymentDto))
                .retrieve()
                .bodyToMono(InitializePaymentResponse.class)
                .block();

        if (response != null && response.getStatus().equals(true)) {
            BigDecimal amount = initializePaymentDto.getAmount().divide(BigDecimal.TEN.pow(2));
            PaymentPayStack user = PaymentPayStack.builder()
                    .amount(amount)
                    .ipAddress(response.getData().getAuthorization_url())
                    .reference(response.getData().getReference())
                    .currency("NGN")
                    .build();
            paystackPaymentRepository.save(user);
            return response;
        } else {
            return InitializePaymentResponse.builder()
                    .message("Paystack is unable to initialize payment at the moment")
                    .build();
        }
    }


    @Override
    public PaymentVerificationResponse paymentVerification(String reference) throws Exception {
        PaymentVerificationResponse paymentVerificationResponse;


        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(ResponseUtils.PAYSTACK_VERIFY + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + payStackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == ResponseUtils.STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to verify payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();
            paymentVerificationResponse = mapper.readValue(result.toString(), PaymentVerificationResponse.class);

            if (paymentVerificationResponse == null || paymentVerificationResponse.getStatus().equals("false")) {
                throw new Exception("An error");
            } else if (paymentVerificationResponse.getData().getStatus().equals("success")) {

                String email = paymentVerificationResponse.getData().getCustomer().getEmail();
                String name = paymentVerificationResponse.getData().getCustomer().getFirst_name() +" "  +paymentVerificationResponse.getData().getCustomer().getLast_name();
                int id = paymentVerificationResponse.getData().getCustomer().getId();

                PaymentPayStack paymentDetails = paystackPaymentRepository.findByReference(reference);

                paymentDetails.setUserId((long) id);
                paymentDetails.setName(name);
                paymentDetails.setEmail(email);
                paymentDetails.setReference(paymentVerificationResponse.getData().getReference());
                paymentDetails.setAmount(paymentVerificationResponse.getData().getAmount());
                paymentDetails.setGatewayResponse(paymentVerificationResponse.getData().getGateway_response());
                paymentDetails.setPaidAt(paymentVerificationResponse.getData().getPaidAt());
                paymentDetails.setCreatedAt(paymentVerificationResponse.getData().getCreatedAt());
                paymentDetails.setChannel(paymentVerificationResponse.getData().getChannel());
                paymentDetails.setCurrency(paymentVerificationResponse.getData().getCurrency());
                paymentDetails.setIpAddress(paymentVerificationResponse.getData().getIp_address());                paymentDetails.setCreatedOn(LocalDateTime.now());

                paystackPaymentRepository.save(paymentDetails);
                return paymentVerificationResponse;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return paymentVerificationResponse;
    }




}

