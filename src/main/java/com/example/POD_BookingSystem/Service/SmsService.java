package com.example.POD_BookingSystem.Service;

//import com.twilio.rest.verify.v2.service.VerificationCheck;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Slf4j
public class SmsService {
    @Value("${sms.url}")
    private String apiUrl;

    @Value("${sms.token}")
    private String accessToken;

    public String getUserInfo() {
        String url = "https://api.speedsms.vn/index.php/user/info";
        HttpHeaders headers = createHeaders();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        return response.getBody();
    }



    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        String auth = "Basic " + Base64.getEncoder().encodeToString((accessToken + ":").getBytes(StandardCharsets.UTF_8));
        headers.set("Authorization", auth);
        return headers;
    }
}


