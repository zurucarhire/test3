package com.cellulant.iprs.dto.mpesa;

import org.springframework.http.HttpStatus;

/**
 * Created by abala on 10/14/18.
 */
public class B2CResponse {
    private String statusCode;
    private HttpStatus httpStatus;
    private int errorCode;
    private String errorMessage;
    private B2C b2c;

    public B2CResponse(String statusCode, HttpStatus httpStatus, int errorCode, String errorMessage) {
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public B2CResponse(String statusCode, HttpStatus httpStatus, int errorCode, String errorMessage, B2C b2c) {
        this.statusCode = statusCode;
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.b2c = b2c;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public B2C getB2c() {
        return b2c;
    }

    public void setB2c(B2C b2c) {
        this.b2c = b2c;
    }

    @Override
    public String toString(){
        return "statusCode="+statusCode+"httpStatus="+httpStatus+"errorCode="+errorCode+
                "errorMessage="+errorMessage+"b2c="+b2c;
    }
}
