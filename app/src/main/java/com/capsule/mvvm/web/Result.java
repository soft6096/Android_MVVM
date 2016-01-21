package com.capsule.mvvm.web;

/**
 * Created by 宇宙神帝 on 2014/11/23.
 */
public class Result {

    public boolean flag;

    public String error;

    public Object value;

    private String total;

    public Result() {
        flag = false;
        error = "不可访问";
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
