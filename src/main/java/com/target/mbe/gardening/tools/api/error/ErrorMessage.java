package com.target.mbe.gardening.tools.api.error;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;

public class ErrorMessage
{
    int status;
    int code;
    String message;
    String developerMessage;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public ErrorMessage(AppException ex)
    {
        this.status = ex.getStatus();
        this.code = ex.getCode();
        this.message = ex.getMessage();
        this.developerMessage = ex.getDeveloperMessage();
    }

    public ErrorMessage(NotFoundException ex)
    {
        this.status = Response.Status.NOT_FOUND.getStatusCode();
        this.message = ex.getMessage();
    }

    public ErrorMessage() {}
}