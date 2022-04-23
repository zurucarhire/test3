package com.cellulant.iprs.dto.mpesa;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class Result {
    private String ResultType;
    private String ResultCode;
    private String ResultDesc;
    private String OriginatorConversationID;
    private String ConversationID;
    private String TransactionID;
    private Map<String, Object> ResultParameters;

    public String getResultType() {
        return ResultType;
    }

    @JsonProperty("ResultType")
    public void setResultType(String resultType) {
        ResultType = resultType;
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

    public String getOriginatorConversationID() {
        return OriginatorConversationID;
    }

    @JsonProperty("OriginatorConversationID")
    public void setOriginatorConversationID(String originatorConversationID) {
        OriginatorConversationID = originatorConversationID;
    }

    public String getConversationID() {
        return ConversationID;
    }

    @JsonProperty("ConversationID")
    public void setConversationID(String conversationID) {
        ConversationID = conversationID;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    @JsonProperty("TransactionID")
    public void setTransactionID(String transactionID) {
        TransactionID = transactionID;
    }

    public Map<String, Object> getResultParameters() {
        return ResultParameters;
    }

    @JsonProperty("ResultParameters")
    public void setResultParameters(Map<String, Object> resultParameters) {
        ResultParameters = resultParameters;
    }

    @Override
    public String toString() {
        return "Result{" +
                "ResultType='" + ResultType + '\'' +
                ", ResultCode='" + ResultCode + '\'' +
                ", ResultDesc='" + ResultDesc + '\'' +
                ", OriginatorConversationID='" + OriginatorConversationID + '\'' +
                ", ConversationID='" + ConversationID + '\'' +
                ", TransactionID='" + TransactionID + '\'' +
                ", ResultParameters='" + ResultParameters + '\'' +
                '}';
    }
}
