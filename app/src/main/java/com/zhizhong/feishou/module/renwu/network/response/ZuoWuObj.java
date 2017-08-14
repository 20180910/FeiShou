package com.zhizhong.feishou.module.renwu.network.response;

/**
 * Created by administartor on 2017/8/14.
 */

public class ZuoWuObj {

    /**
     * id : 6
     * nh_order_no : NH145647658
     * image_url : http://121.40.186.118:5009/upload/123.jpg
     * title : 花生/10.00元每亩/500亩
     * address : 东西湖区华阳街道
     * total_price : 5000
     * request_time : 7/10~7/18  (8天)
     * condition : 无障碍,作物高度20-30cm
     * is_andy : 1
     * is_encase : 1
     * is_rechargeable : 1
     * is_getwater : 1
     */

    private int id;
    private String nh_order_no;
    private String image_url;
    private String title;
    private String address;
    private double total_price;
    private String request_time;
    private String condition;
    private int is_andy;
    private int is_encase;
    private int is_rechargeable;
    private int is_getwater;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNh_order_no() {
        return nh_order_no;
    }

    public void setNh_order_no(String nh_order_no) {
        this.nh_order_no = nh_order_no;
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
}
