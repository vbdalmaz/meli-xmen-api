package br.com.melixmen.api.handler;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("error")
public class ResponseError implements Serializable {

    private static final long serialVersionUID = 2019010901L;

    private Integer code;

    private String message;

    public ResponseError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
