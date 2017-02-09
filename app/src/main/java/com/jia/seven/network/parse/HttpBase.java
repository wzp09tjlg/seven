package com.jia.seven.network.parse;

/**
 * Created by tiny on 1/19/2017.
 */
@SuppressWarnings("All")
public class HttpBase<T> {
    private int error_code;
    private String error_msg;
    private String tip_msg;
    private T data;

    public int getErrorCode() {
        return error_code;
    }

    public void setErrorCode(int error_code) {
        this.error_code = error_code;
    }

    public String getTipMsg() {
        return tip_msg;
    }

    public void setTipMsg(String tip_msg) {
        this.tip_msg = tip_msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTip_msg() {
        return tip_msg;
    }

    public void setTip_msg(String tip_msg) {
        this.tip_msg = tip_msg;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
