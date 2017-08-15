package com.zhizhong.feishou.module.my.network.response;

import com.zhizhong.feishou.base.BaseObj;

/**
 * Created by administartor on 2017/8/15.
 */

public class OrderDetailObj extends BaseObj{

    /**
     * order_no : NH112313568
     * order_status : 2
     * region : 天津
     * crops : 早稻
     * price : 13.00元/亩
     * area : 500亩
     * request_time : 2017/8/13~2017/8/17  (4天)
     * status_title :
     * transitions_number : 无
     * transitions_instructions : 无
     * condition : 无障碍,作物高度20-30cm
     * obstacles : 无障碍
     * remark : 无
     * is_andy : 1
     * is_encase : 1
     * is_rechargeable : 1
     * is_getwater : 1
     * address : 和平区华阳街道
     * total_price : 6500元
     */

    private String order_no;
    private int order_status;
    private String region;
    private String crops;
    private String price;
    private String area;
    private String request_time;
    private String status_title;
    private String transitions_number;
    private String transitions_instructions;
    private String condition;
    private String obstacles;
    private String remark;
    private int is_andy;
    private int is_encase;
    private int is_rechargeable;
    private int is_getwater;
    private String address;
    private String total_price;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCrops() {
        return crops;
    }

    public void setCrops(String crops) {
        this.crops = crops;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public String getStatus_title() {
        return status_title;
    }

    public void setStatus_title(String status_title) {
        this.status_title = status_title;
    }

    public String getTransitions_number() {
        return transitions_number;
    }

    public void setTransitions_number(String transitions_number) {
        this.transitions_number = transitions_number;
    }

    public String getTransitions_instructions() {
        return transitions_instructions;
    }

    public void setTransitions_instructions(String transitions_instructions) {
        this.transitions_instructions = transitions_instructions;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getObstacles() {
        return obstacles;
    }

    public void setObstacles(String obstacles) {
        this.obstacles = obstacles;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIs_andy() {
        return is_andy;
    }

    public void setIs_andy(int is_andy) {
        this.is_andy = is_andy;
    }

    public int getIs_encase() {
        return is_encase;
    }

    public void setIs_encase(int is_encase) {
        this.is_encase = is_encase;
    }

    public int getIs_rechargeable() {
        return is_rechargeable;
    }

    public void setIs_rechargeable(int is_rechargeable) {
        this.is_rechargeable = is_rechargeable;
    }

    public int getIs_getwater() {
        return is_getwater;
    }

    public void setIs_getwater(int is_getwater) {
        this.is_getwater = is_getwater;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }
}
