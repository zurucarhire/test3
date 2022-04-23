package com.cellulant.iprs.dto.mpesa;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abala on 10/14/18.
 */
public class ExpressQuery {
    private String ResponseCode;
    private String ResponseDescription;
    private String MerchantRequestID;
    private String CheckoutRequestID;
    private String ResultCode;
    private String ResultDesc;
    private String CustomerMessage;

    @JsonProperty("MerchantRequestID")
    public String getMerchantRequestID() {
        return MerchantRequestID;
    }

    public void setMerchantRequestID(String MerchantRequestID) {
        this.MerchantRequestID = MerchantRequestID;
    }

    @JsonProperty("CheckoutRequestID")
    public String getCheckoutRequestID() {
        return CheckoutRequestID;
    }

    public void setCheckoutRequestID(String CheckoutRequestID) {
        this.CheckoutRequestID = CheckoutRequestID;
    }

    @JsonProperty("ResponseCode")
    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String ResponseCode) {
        this.ResponseCode = ResponseCode;
    }

    @JsonProperty("ResponseDescription")
    public String getResponseDescription() {
        return ResponseDescription;
    }

    public void setResponseDescription(String ResponseDescription) {
        this.ResponseDescription = ResponseDescription;
    }

    @JsonProperty("ResultCode")
    public String getResultCode() {
        return ResultCode;
    }

    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    @JsonProperty("ResultDesc")
    public String getResultDesc() {
        return ResultDesc;
    }

    public void setResultDesc(String resultDesc) {
        ResultDesc = resultDesc;
    }

    @JsonProperty("CustomerMessage")
    public String getCustomerMessage() {
        return CustomerMessage;
    }

    public void setCustomerMessage(String customerMessage) {
        CustomerMessage = customerMessage;
    }
}
