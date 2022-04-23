package com.cellulant.iprs.dto.mpesa;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class StkCallBack {
    private String MerchantRequestID;
    private String CheckoutRequestID;
    private String ResultCode;
    private String ResultDesc;
    private Map<String, Object> CallbackMetadata;

    public String getMerchantRequestID() {
        return MerchantRequestID;
    }

    @JsonProperty("MerchantRequestID")
    public void setMerchantRequestID(String merchantRequestID) {
        MerchantRequestID = merchantRequestID;
    }

    public String getCheckoutRequestID() {
        return CheckoutRequestID;
    }

    @JsonProperty("CheckoutRequestID")
    public void setCheckoutRequestID(String checkoutRequestID) {
        CheckoutRequestID = checkoutRequestID;
    }

    public String getResultCode() {
        return ResultCode;
    }

    @JsonProperty("ResultCode")
    public void setResultCode(String resultCode) {
        ResultCode = resultCode;
    }

    public String getResultDesc() {
        return ResultDesc;
    }

    @JsonProperty("ResultDesc")
    public void setResultDesc(String resultDesc) {
        ResultDesc = resultDesc;
    }

    public Map<String, Object> getCallbackMetadata() {
        return CallbackMetadata;
    }

    @JsonProperty("CallbackMetadata")
    public void setCallbackMetadata(Map<String, Object> callbackMetadata) {
        CallbackMetadata = callbackMetadata;
    }

    @Override
    public String toString() {
        return "StkCallBack{" +
                "MerchantRequestID='" + MerchantRequestID + '\'' +
                ", CheckoutRequestID='" + CheckoutRequestID + '\'' +
                ", ResultCode='" + ResultCode + '\'' +
                ", ResultDesc='" + ResultDesc + '\'' +
                ", CallbackMetadata='" + CallbackMetadata + '\'' +
                '}';
    }
}
