package com.cellulant.iprs.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public static String SECRET_KEY;
    public static String CUSTOMER_PASS;
    public static String UPLOAD_PATH;
    public static String MPESA_PAYBILL_SHORTCODE;
    public static String MPESA_TOKEN_URI;
    public static String MPESA_EXPRESS_URI;
    public static String MPESA_EXPRESS_QUERY_URI;
    public static String MPESA_EXPRESS_PASSWORD;
    public static String MPESA_EXPRESS_CONSUMER_KEY;
    public static String MPESA_EXPRESS_CONSUMER_SECRET;
    public static String MPESA_EXPRESS_TRANSACTION_TYPE;
    public static String MPESA_EXPRESS_CALLBACK_URL;
    public static String MPESA_EXPRESS_ACCOUNT_REFERENCE;
    public static String MPESA_EXPRESS_TRANSACTION_DESC;
    public static String MPESA_C2B_REGISTER_URI;
    public static String MPESA_C2B_VALIDATION_URI;
    public static String MPESA_C2B_CONFIRMATION_URI;
    public static String MPESA_B2C_URI;
    public static String MPESA_B2C_SHORTCODE;
    public static String MPESA_B2C_INITIATOR_NAME;
    public static String MPESA_B2C_SECURITY_CREDENTIAL;
    public static String MPESA_B2C_CONSUMER_KEY;
    public static String MPESA_B2C_CONSUMER_SECRET;
    public static String MPESA_B2C_COMMAND_ID;
    public static String MPESA_B2C_REMARKS;
    public static String MPESA_B2C_OCCASSION;
    public static String MPESA_B2C_QUEUE_TIMEOUT_URL;
    public static String MPESA_B2C_RESULT_URL;
    public static String AFRICASTALKING_USERNAME;
    public static String AFRICASTALKING_PASSWORD;


//    public static String AficasTalkingUsername;
//    public static String AficasTalkingApiKey;

    @Value( "${jwt.secret}")
    public void setSecretKey(String param) {
        SECRET_KEY = param;
    }

    @Value( "${customer.pass}")
    public void setCustomerPass(String param) {
        CUSTOMER_PASS = param;
    }

    @Value( "${upload.path}")
    public void setUploadPath(String param) {
        UPLOAD_PATH = param;
    }

    @Value( "${mpesa.paybill.shortcode}")
    public void setMpesaPaybillShortcode(String param) {
        MPESA_PAYBILL_SHORTCODE = param;
    }


    @Value( "${mpesa.token.uri}")
    public void setMpesaTokenUri(String param) {
        MPESA_TOKEN_URI = param;
    }

    @Value( "${mpesa.express.uri}")
    public void setMpesaExpressUri(String param) {
        MPESA_EXPRESS_URI = param;
    }

    @Value( "${mpesa.express.query.uri}")
    public void setMpesaExpressQueryUri(String param) {
        MPESA_EXPRESS_QUERY_URI = param;
    }

    @Value( "${mpesa.express.password}")
    public void setMpesaExpressPassword(String param) {
        MPESA_EXPRESS_PASSWORD = param;
    }

    @Value( "${mpesa.express.consumer.key}")
    public void setExpressMpesaConsumerKey(String param) {
        MPESA_EXPRESS_CONSUMER_KEY = param;
    }

    @Value( "${mpesa.express.consumer.secret}")
    public void setExpressMpesaConsumerSecret(String param) {
        MPESA_EXPRESS_CONSUMER_SECRET = param;
    }

    @Value( "${mpesa.express.transaction.type}")
    public void setMpesaExpressTransactionType(String param) {
        MPESA_EXPRESS_TRANSACTION_TYPE = param;
    }

    @Value( "${mpesa.express.callback.url}")
    public void setMpesaExpressCallbackUrl(String param) {
        MPESA_EXPRESS_CALLBACK_URL = param;
    }

    @Value( "${mpesa.express.account.reference}")
    public void setMpesaExpressAccountReference(String param) {
        MPESA_EXPRESS_ACCOUNT_REFERENCE = param;
    }

    @Value( "${mpesa.express.transaction.desc}")
    public void setMpesaExpressTransactionDesc(String param) {
        MPESA_EXPRESS_TRANSACTION_DESC = param;
    }

    @Value( "${mpesa.c2b.register.url}")
    public void setMpesaC2bRegisterUri(String param) {
        MPESA_C2B_REGISTER_URI = param;
    }

    @Value( "${mpesa.c2b.validation.url}")
    public void setMpesaC2bValidationUri(String param) {
        MPESA_C2B_VALIDATION_URI = param;
    }

    @Value( "${mpesa.c2b.confirmation.url}")
    public void setMpesaC2bConfirmationUri(String param) {
        MPESA_C2B_CONFIRMATION_URI = param;
    }

    @Value( "${mpesa.b2c.uri}")
    public void setMpesaB2cUrii(String param) {
        MPESA_B2C_URI = param;
    }

    @Value( "${mpesa.b2c.shortcode}")
    public void setMpesaB2cShortcode(String param) {
        MPESA_B2C_SHORTCODE = param;
    }

    @Value( "${mpesa.b2c.initiator.name}")
    public void setMpesaB2cInitiatorName(String param) {
        MPESA_B2C_INITIATOR_NAME = param;
    }

    @Value( "${mpesa.b2c.security.credential}")
    public void setMpesaB2cSecurityCredential(String param) {
        MPESA_B2C_SECURITY_CREDENTIAL = param;
    }

    @Value( "${mpesa.b2c.consumer.key}")
    public void setMpesaConsumerKey(String param) {
        MPESA_B2C_CONSUMER_KEY = param;
    }

    @Value( "${mpesa.b2c.consumer.secret}")
    public void setMpesaConsumerSecret(String param) {
        MPESA_B2C_CONSUMER_SECRET = param;
    }

    @Value( "${mpesa.b2c.command.id}")
    public void setMpesaB2cCommandId(String param) {
        MPESA_B2C_COMMAND_ID = param;
    }

    @Value( "${mpesa.b2c.remarks}")
    public void setMpesaB2cRemarks(String param) {
        MPESA_B2C_REMARKS = param;
    }

    @Value( "${mpesa.b2c.occassion}")
    public void setMpesaB2cOccassion(String param) {
        MPESA_B2C_OCCASSION = param;
    }

    @Value( "${mpesa.b2c.queuetimeout.url}")
    public void setMpesaB2cQueueTimeoutUrl(String param) {
        MPESA_B2C_QUEUE_TIMEOUT_URL= param;
    }

    @Value( "${mpesa.b2c.result.url}")
    public void setMpesaB2cResultUrl(String param) {
        MPESA_B2C_RESULT_URL= param;
    }

    @Value( "${africas.talking.username}")
    public void setAficasTalkingUsername(String param) {
        AFRICASTALKING_USERNAME = param;
    }

    @Value( "${africas.talking.api.key}")
    public void setAficasTalkingApiKey(String param) {
        AFRICASTALKING_PASSWORD = param;
    }

}

