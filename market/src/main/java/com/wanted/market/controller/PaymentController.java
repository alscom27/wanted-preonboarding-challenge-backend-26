package com.wanted.market.controller;

import com.wanted.market.model.*;
import com.wanted.market.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

  @Autowired
  private PaymentService paymentService;

  @PostMapping("/create")
  public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest paymentRequest) {
    PaymentResponse response = paymentService.createPayment(paymentRequest);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/cancel")
  public ResponseEntity<PaymentResponse> cancelPayment(@RequestBody CancelRequest cancelRequest) {
    PaymentResponse response = paymentService.cancelPayment(cancelRequest);
    return ResponseEntity.ok(response);
  }

  @PostMapping("/virtual-account")
  public ResponseEntity<VirtualAccountResponse> createVirtualAccount(@RequestBody VirtualAccountRequest request) {
    VirtualAccountResponse response = paymentService.createVirtualAccount(request);
    return ResponseEntity.ok(response);
  }
}
