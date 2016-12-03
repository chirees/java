
package com.framework.web.bean;

import java.io.Serializable;

public class ApiResult<T> implements Serializable{

    /**  */
    private static final long serialVersionUID = -370102839420094779L;
    private String resultCode;
    private String resultMsg;
    private T object;
    public String getResultCode() {
        return resultCode;
    }
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }
    public String getResultMsg() {
        return resultMsg;
    }
    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
    public T getObject() {
        return object;
    }
    public void setObject(T object) {
        this.object = object;
    }
}
