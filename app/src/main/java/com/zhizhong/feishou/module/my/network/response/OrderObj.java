package com.zhizhong.feishou.module.my.network.response;

/**
 * Created by administartor on 2017/8/15.
 */

public class OrderObj {

    /**
     * order_no : NH174164789
     * image_url : http://121.40.186.118:5009/upload/123.jpg
     * title : 早稻/12元每亩/500亩
     * address : 长宁区华阳街道
     * total_price : 6000
     * request_time : 6/15~6/24  (9天)
     * condition : 无障碍,作物高度20-30cm
     * status_title :
     * order_status : 1
     */

    private String order_no;
    private String image_url;
    private String title;
    private String address;
    private double total_price;
    private String request_time;
    private String condition;
    private String status_title;
    private int order_status;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getStatus_title() {
        return status_title;
    }

    public void setStatus_title(String status_title) {
        this.status_title = status_title;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }
}
