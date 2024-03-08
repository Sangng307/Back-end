package com.poly.demo.payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import jakarta.servlet.http.HttpSession;



@RestController
@RequestMapping("/api/payment")
public class paywithmomo {
	
	@Autowired
	HttpSession ses;
	
	public static String PAY_URL = "payUrl";
	
	@PostMapping("/momopay")
    public String main(@RequestParam String userId,
            @RequestParam Long amount) {
        String endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";        
        
        String partnerCode = "MOMOBKUN20180529";
        String accessKey = "klm05TvNBzhg7h7j";
        String secretKey = "at67qH6mk8w5Y1nAyMoYKMWACiEi2bsa";
        String orderInfo = userId;
        long Amount = amount;
        String orderId = String.valueOf(System.currentTimeMillis());
        String redirectUrl = "http://localhost:3000";
        String ipnUrl = "https://webhook.site/b3088a6a-2d17-4f8d-a383-71389a6c600b";
        String extraData = "";

        try {
            String requestId = String.valueOf(System.currentTimeMillis());
            String requestType = "payWithATM";
            extraData = (extraData != null ? extraData : "");

            // Before signing HMAC SHA256 signature
            String rawHash = "accessKey=" + accessKey +
                            "&amount=" + amount +
                            "&extraData=" + extraData +
                            "&ipnUrl=" + ipnUrl +
                            "&orderId=" + orderId +
                            "&orderInfo=" + orderInfo +
                            "&partnerCode=" + partnerCode +
                            "&redirectUrl=" + redirectUrl +
                            "&requestId=" + requestId +
                            "&requestType=" + requestType;
            
            String signature = calculateHMACSHA256(rawHash, secretKey);

            Map<String, String> data = new HashMap<>();
            data.put("partnerCode", partnerCode);
            data.put("partnerName", "Test");
            data.put("storeId", "MomoTestStore");
            data.put("requestId", requestId);
            data.put("amount", String.valueOf(Amount));
            data.put("orderId", orderId);
            data.put("orderInfo", orderInfo);
            data.put("redirectUrl", redirectUrl);
            data.put("ipnUrl", ipnUrl);
            data.put("lang", "vi");
            data.put("extraData", extraData);
            data.put("requestType", requestType);
            data.put("signature", signature);

            String result = execPostRequest(endpoint, data);
            String payURL = getPayURLFromResult(result);
            System.out.println("result "+result);
            System.out.println("result "+payURL);
            PAY_URL = payURL;
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PAY_URL;
    }
	public static String getPayURLFromResult(String result) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode jsonNode = objectMapper.readTree(result);

	        if (jsonNode.has("payUrl")) {
	            String payURL = jsonNode.get("payUrl").asText();
	            return payURL;
	        } else {
	            System.out.println("ko thấy ủl");
	            return null;
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	

    public static String calculateHMACSHA256(String data, String secretKey) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            return bytesToHex(rawHmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public static String execPostRequest(String url, Map<String, String> data) throws IOException {
        URL endpoint = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) endpoint.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Set headers
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Content-Length", String.valueOf(data.size()));

        // Prepare data to send
        String dataToSend = new ObjectMapper().writeValueAsString(data);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = dataToSend.getBytes("UTF-8");
            os.write(input, 0, input.length);
        }

        String response;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"))) {
            StringBuilder responseBuilder = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                responseBuilder.append(responseLine.trim());
            }
            response = responseBuilder.toString();
        }
        System.out.println("prinf "+connection);
        return response;
    }
}