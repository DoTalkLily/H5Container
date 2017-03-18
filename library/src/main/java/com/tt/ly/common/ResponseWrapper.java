package com.tt.ly.common;

/**
 * Created by Seven on 17/3/17.
 */

public class ResponseWrapper {
    private int errCode;

    private String errMsg;

    private Object data;

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean isError(){
        return errCode == Consts.ERROR;
    }
}
