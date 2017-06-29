package com.tt.ly.common;

import com.tt.ly.offlinepackage.ResourceRecord;

import java.util.List;

/**
 * Created by Seven on 17/3/17.
 */

public class ResponseWrapper {
    private int errCode;

    private String errMsg;

    private List<ResourceRecord> data;

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

    public List<ResourceRecord> getData() {
        return data;
    }

    public void setData(List<ResourceRecord> data) {
        this.data = data;
    }

    public boolean isError(){
        return errCode == Consts.ERROR;
    }
}
