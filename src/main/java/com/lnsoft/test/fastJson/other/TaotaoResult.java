package com.lnsoft.test.fastJson.other;

import java.io.Serializable;


/**
 * 自定义响应结构
 * 
 */
public class TaotaoResult implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static TaotaoResult build(Integer status, String msg, Object data) {
        return new TaotaoResult(status, msg, data);
    }

    public static TaotaoResult build(Integer status, String msg) {
        return new TaotaoResult(status, msg, null);
    }
    public static TaotaoResult ok(Object data) {
        return new TaotaoResult(data);
    }

    public static TaotaoResult ok() {
        return new TaotaoResult(null);
    }

    public TaotaoResult() {

    }

    public TaotaoResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public TaotaoResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

   

}
