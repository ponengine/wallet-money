package com.pon.wallet.domain;

public class BaseRestApi {

    private boolean success;
    private BaseResponse response;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BaseResponse getResponse() {
        return response;
    }

    public void setResponse(BaseResponse response) {
        this.response = response;
    }

}
