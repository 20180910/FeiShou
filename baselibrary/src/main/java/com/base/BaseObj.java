package com.base;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/20.
 */

public class BaseObj implements Serializable {
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
