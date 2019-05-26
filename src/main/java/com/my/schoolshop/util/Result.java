package com.my.schoolshop.util;

public class Result<T> {

    private boolean success;
    private T data;
    private String errorMsg;
    private int errorCode;

    public Result(){

    }

    //成功构造器
    public Result(boolean success,T data){
        this.success = success;
        this.data = data;
    }

    //错误时构造器
    public Result(boolean success,int errorCode,String errorMsg){
        this.success = success;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public  boolean isSuccess(){
        return  success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public T getData() {
        return data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
