package com.cellulant.iprs.service;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class IPRSSoapConnector extends WebServiceGatewaySupport {

    public Object callWebService(String url, Object request){
        return getWebServiceTemplate().marshalSendAndReceive(url, request);
    }
}
