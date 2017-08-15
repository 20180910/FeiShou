package com.zhizhong.feishou.module.my.event;

/**
 * Created by administartor on 2017/8/15.
 */

public class OrderEvent {
    public String type;
    public String orderNo;

    public OrderEvent(String type, String orderNo) {
        this.type = type;
        this.orderNo = orderNo;
    }
}
