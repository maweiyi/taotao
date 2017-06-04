package me.maweiyi.bean;

/**
 * Created by maweiyi on 6/1/17.
 */
public class HttpResult {

    private Integer Code;

    private String body;

    public HttpResult(Integer code, String body) {
        Code = code;
        this.body = body;
    }

    public HttpResult() {
    }

    public Integer getCode() {
        return Code;
    }

    public void setCode(Integer code) {
        Code = code;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
