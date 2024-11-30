package com.wanted.market.service;

import com.wanted.market.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class PaymentService {

  private final String PORTONE_API_URL = "https://api.portone.kr"; // 포트원 API URL
  private RestTemplate restTemplate = new RestTemplate();

  public PaymentResponse createPayment(PaymentRequest paymentRequest) {
    // 포트원 API 호출
    // 실제 API 호출을 위한 URL과 요청 설정 필요
    String apiUrl = PORTONE_API_URL + "/payments"; // 예시 URL
    ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(apiUrl, paymentRequest, PaymentResponse.class);
    return response.getBody();
  }

  public PaymentResponse cancelPayment(CancelRequest cancelRequest) {
    // 결제 취소 로직 구현
    String apiUrl = PORTONE_API_URL + "/payments/cancel"; // 예시 URL
    ResponseEntity<PaymentResponse> response = restTemplate.postForEntity(apiUrl, cancelRequest, PaymentResponse.class);
    return response.getBody();
  }

  public VirtualAccountResponse createVirtualAccount(VirtualAccountRequest request) {
    // 가상계좌 생성 로직 구현
    String apiUrl = PORTONE_API_URL + "/virtual-accounts"; // 예시 URL
    ResponseEntity<VirtualAccountResponse> response = restTemplate.postForEntity(apiUrl, request, VirtualAccountResponse.class);
    return response.getBody();
  }
}
