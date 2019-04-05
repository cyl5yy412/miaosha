package com.lnsoft.response;


/**
 * 自定义返回体
 * Created By Chr on 2019/4/5/0005.
 */

public class ChrReturnResult {

    private String status;

    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ChrReturnResult() {
    }

    public ChrReturnResult(Object data) {
        this.status = "suc";
        this.data = data;
    }

    public ChrReturnResult(String status, Object data) {
        this.status = status;
        this.data = data;
    }



    public static ChrReturnResult create() {
        return new ChrReturnResult();
    }

    public static ChrReturnResult create(Object data) {
        return new ChrReturnResult(data);
    }

    public static ChrReturnResult create(String status, Object data) {
        return new ChrReturnResult(status, data);
    }
}
