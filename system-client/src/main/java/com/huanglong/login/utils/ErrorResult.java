package com.huanglong.login.utils;


import org.springframework.http.HttpStatus;

public  class ErrorResult {
    private int statusCode; // 数字状态码
    private String message; // 信息
    private HttpStatus statusError; // 枚举状态码

    public ErrorResult() {}
    public ErrorResult(int statusCode, String message, HttpStatus statusError) {
        this.statusCode = statusCode;
        this.message = message;
        this.statusError = statusError;
    }

    public void setStatusCode(int code) {
        this.statusCode = code;
    }
    public int getStatusCode() {
        return this.statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return this.message;
    }

    public void setError(HttpStatus statusError) {
        this.statusError = statusError;
    }
    public HttpStatus getError() {
        return this.statusError;
    }
}
