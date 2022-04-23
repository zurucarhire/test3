package com.cellulant.iprs.dto.mpesa;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by abala on 10/14/18.
 */
public class B2C {
    private String OriginatorConversationID;
    private String ConversationID;
    private String ResponseCode;
    private String ResponseDescription;

    @JsonProperty("OriginatorConversationID")
    public String getOriginatorConversationID() {
        return OriginatorConversationID;
    }

    public void setOriginatorConversationID(String originatorConversationID) {
        OriginatorConversationID = originatorConversationID;
    }

    @JsonProperty("ConversationID")
    public String getConversationID() {
        return ConversationID;
    }

    public void setConversationID(String conversationID) {
        ConversationID = conversationID;
    }

    @JsonProperty("ResponseCode")
    public String getResponseCode() {
        return ResponseCode;
    }

    public void setResponseCode(String responseCode) {
        ResponseCode = responseCode;
    }

    @JsonProperty("ResponseDescription")
    public String getResponseDescription() {
        return ResponseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        ResponseDescription = responseDescription;
    }

    @Override
    public String toString(){
        return "OriginatorConversationID="+OriginatorConversationID+"ConversationID="+ConversationID+
                "ResponseCode="+ResponseCode+"ResponseDescription="+ResponseDescription;

    }
}
