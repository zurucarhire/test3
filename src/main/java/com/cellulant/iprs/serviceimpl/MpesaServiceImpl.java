package com.cellulant.iprs.serviceimpl;

import com.cellulant.iprs.configuration.AppConfig;
import com.cellulant.iprs.dto.mpesa.*;
import com.cellulant.iprs.entity.Payment;
import com.cellulant.iprs.repository.PaymentRepository;
import com.cellulant.iprs.service.IMpesaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Collections;

/**
 * @author joeabala
 *
 * Mpesa Service Implimentation
 */

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MpesaServiceImpl implements IMpesaService {

    private static final String ALPHABETIC_STRING= "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private ObjectMapper objectMapper = new ObjectMapper();
    private final PaymentRepository paymentRepository;

    @Override
    public void express(ExpressRequest express) {
        log.info("express {}", express);

        String token = getAccessTokenExpress();
        Long userId = express.getUserID();
        Long productId = express.getProductID();
        String msisdn = express.getMsisdn();
        double amount = express.getAmount();

        HttpHeaders headers = new HttpHeaders();
        String request = formatRequestExpress("174379",
                "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMjIwNDIzMDIxMDIx",
                "20220423021021",
                "CustomerPayBillOnline",
                "1", "254708374149",
                "174379", "254717729123",
                "https://mydomain.com/path",
                "CompanyXLTD",
                "Payment of X");

        HttpComponentsClientHttpRequestFactory requestFactory = createTrustStrategy();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Bearer " + "gfGvQQugmFve2pJfXZrtAAv3tw9R");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(request, headers);
        RestTemplate restTemplate = new RestTemplate(requestFactory);

        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest", HttpMethod.POST, requestEntity, String.class);
            log.info("ResponseEntity {} {}", responseEntity.getBody(), responseEntity.getBody().getClass());
            ExpressQuery expressQuery = objectMapper.readValue(responseEntity.getBody(), ExpressQuery.class);
            log.info("Express {} {} {} {} {}", expressQuery.getResponseCode(), expressQuery.getCheckoutRequestID(),
                    expressQuery.getResultCode(), expressQuery.getMerchantRequestID(), expressQuery.getResponseDescription());
            Payment payment = new Payment();
            payment.setUserID(userId);
            payment.setProductID(productId);
            payment.setTransactionID(System.currentTimeMillis() + randomAlphabetic(2));
            payment.setMpesaexpressmerchantrequestid(expressQuery.getMerchantRequestID());
            payment.setMpesaexpresscheckoutrequestid(expressQuery.getCheckoutRequestID());
            payment.setMpesaexpressresponsecode(expressQuery.getResponseCode());
            payment.setMpesaexpressresponsedescription(expressQuery.getResponseDescription());
            payment.setAmount(amount);
            payment.setPaymentstatus("pending");
            payment.setStatus("pending");
            paymentRepository.save(payment);

        } catch (IOException e) {
            e.printStackTrace();
            log.info("Express IOException {} ", e.getLocalizedMessage());

        } catch (Exception e) {
            e.printStackTrace();
            log.info("Express Exception {} ", e.getLocalizedMessage());

        }
    }

    @Override
    public void expressCallback(ExpressCallback expressCallback) {
        log.info("expressCallback Results {}", expressCallback);
        StkCallBack stkCallBack = expressCallback.getBody().get("stkCallback");
        String checkoutRequestID = stkCallBack.getCheckoutRequestID();
        String merchantRequestID = stkCallBack.getMerchantRequestID();
        String resultCode = stkCallBack.getResultCode();
        String resultDesc = stkCallBack.getResultDesc();
        log.info("expressCallback Params {} {} {} {}", checkoutRequestID, merchantRequestID, resultCode, resultDesc);

    }

    private String formatRequestExpress(String businessShortCode, String password, String timestamp, String transactionType,
                                        String amount, String partyA, String partyB, String phoneNumber, String callbackUrl,
                                        String accountReference, String transactionDesc){
        return String.format("{\"BusinessShortCode\": \"%s\",\"Password\": \"%s\"," +
                        "\"Timestamp\": \"%s\",\"TransactionType\": \"%s\",\"Amount\": \"%s\",\"PartyA\": \"%s\"," +
                        "\"PartyB\": \"%s\",\"PhoneNumber\": \"%s\",\"CallBackURL\": \"%s\",\"AccountReference\": \"%s\"," +
                        "\"TransactionDesc\": \"%s\"}", businessShortCode, password, timestamp, transactionType, amount, partyA,
                partyB, phoneNumber, callbackUrl, accountReference, transactionDesc);
    }

    private String formatRequestQuery(String businessShortCode, String password, String timestamp, String checkoutRequestId){
        return String.format("{\"BusinessShortCode\": \"%s\",\"Password\": \"%s\"," +
                        "\"Timestamp\": \"%s\",\"CheckoutRequestID\": \"%s\"}",
                businessShortCode, password, timestamp, checkoutRequestId);
    }

    private String formatRequestC2bRegister(String shortCode, String responseType, String confirmationURL, String validationURL){
        return String.format("{\"ShortCode\": \"%s\",\"ResponseType\": \"%s\"," +
                        "\"ConfirmationURL\": \"%s\","+ "\"ValidationURL\": \"%s\"}",
                shortCode, responseType, confirmationURL, validationURL);
    }


    public String formatRequestB2C(String initiatorName, String securityCredential, String commandID, String amount,
                                   String partyA, String partyB, String remarks, String queueTimeOutURL, String resultURL,
                                   String occassion){

        return String.format("{\"InitiatorName\": \"%s\",\"SecurityCredential\": \"%s\"," +
                        "\"CommandID\": \"%s\",\"Amount\": \"%s\"," +
                        "\"PartyA\": \"%s\",\"PartyB\": \"%s\",\"Remarks\": \"%s\"," +
                        "\"QueueTimeOutURL\": \"%s\",\"ResultURL\": \"%s\",\"Occasion\": \"%s\"}",
                initiatorName, securityCredential, commandID, amount, partyA, partyB, remarks, queueTimeOutURL, resultURL, occassion);
    }

    private String getAccessTokenExpress() {
        HttpHeaders headers = new HttpHeaders();
        String consumerKeyConsumerSecret = "x3iWA8AlCwPo21f71f86DcNpI3QsSTA6".concat(":").concat("a8mCTGwTS3Iny6Bb");
        //String consumerKeyConsumerSecret = AppConfig.MPESA_EXPRESS_CONSUMER_KEY.concat(":").concat(AppConfig.MPESA_EXPRESS_CONSUMER_SECRET);
        byte[] consumerKeyConsumerSecretBytes = consumerKeyConsumerSecret.getBytes();
        byte[] consumerKeyConsumerSecretBytesBase64 = Base64.encodeBase64(consumerKeyConsumerSecretBytes);
        String consumerKeyConsumerSecretBytesBase64String = new String(consumerKeyConsumerSecretBytesBase64);
        headers.add("Authorization", "Basic " + consumerKeyConsumerSecretBytesBase64String);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AccessToken> responseEntity = restTemplate.exchange(AppConfig.MPESA_TOKEN_URI, HttpMethod.GET, request, AccessToken.class);
        //ResponseEntity<AccessToken> responseEntity = restTemplate.exchange("https://api.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials", HttpMethod.GET, request, AccessToken.class);
        String accessToken = responseEntity.getBody().getAccess_token();
        String accessTokenExpiry = responseEntity.getBody().getExpires_in();
        log.info("AccessToken Express {} - {}", accessToken, accessTokenExpiry);
        return accessToken;
    }

    public String getAccessTokenB2C() {
        HttpHeaders headers = new HttpHeaders();
        //String consumerKeyConsumerSecret = "dADlYwe4k4Cd2DmYu1TP11NYmJPPx3fA".concat(":").concat("08BnfOjAgUefX0bk");
        String consumerKeyConsumerSecret = AppConfig.MPESA_B2C_CONSUMER_KEY.concat(":").concat(AppConfig.MPESA_B2C_CONSUMER_SECRET);
        byte[] consumerKeyConsumerSecretBytes = consumerKeyConsumerSecret.getBytes();
        byte[] consumerKeyConsumerSecretBytesBase64 = Base64.encodeBase64(consumerKeyConsumerSecretBytes);
        String consumerKeyConsumerSecretBytesBase64String = new String(consumerKeyConsumerSecretBytesBase64);
        headers.add("Authorization", "Basic " + consumerKeyConsumerSecretBytesBase64String);
        HttpEntity<String> request = new HttpEntity<String>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AccessToken> responseEntity = restTemplate.exchange(AppConfig.MPESA_TOKEN_URI, HttpMethod.GET, request, AccessToken.class);
        //ResponseEntity<AccessToken> responseEntity = restTemplate.exchange("https://api.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials", HttpMethod.GET, request, AccessToken.class);
        String accessToken = responseEntity.getBody().getAccess_token();
        String accessTokenExpiry = responseEntity.getBody().getExpires_in();

        log.info("AccessToken B2C {} - {}", accessToken, accessTokenExpiry);

        return accessToken;
    }

    /**
     * Necessary when invoking https API
     *
     * @return
     */
    private HttpComponentsClientHttpRequestFactory createTrustStrategy (){
        HttpComponentsClientHttpRequestFactory requestFactory = null;
        try {
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
            SSLContext sslContext;
            sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
            requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            log.error("KeyManagementException | NoSuchAlgorithmException | KeyStoreException {}", e.getLocalizedMessage());
            //response = new Response(e.getMessage(), HttpStatus.NETWORK_AUTHENTICATION_REQUIRED, -1, "failed");
        } catch(HttpClientErrorException e) {
            log.error("KeyManagementException | NoSuchAlgorithmException | KeyStoreException {}", e.getResponseBodyAsString());
            // response = new Response(e.getResponseBodyAsString(), HttpStatus.SERVICE_UNAVAILABLE, -1, "failed");
        } catch(Exception e) {
            log.error("Exception {}", e.getMessage());
            // response = new Response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, -1, "failed");
        }
        return requestFactory;
    }

    public static String randomAlphabetic(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHABETIC_STRING.length());
            builder.append(ALPHABETIC_STRING.charAt(character));
        }
        return builder.toString();
    }
}
