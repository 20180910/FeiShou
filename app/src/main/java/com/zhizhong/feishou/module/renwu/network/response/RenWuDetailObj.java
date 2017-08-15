package com.zhizhong.feishou.module.renwu.network.response;

import com.zhizhong.feishou.base.BaseObj;

/**
 * Created by administartor on 2017/8/14.
 */

public class RenWuDetailObj extends BaseObj{

    /**
     * id : 1
     * image_url : http://121.40.186.118:5009/upload/123.jpg
     * nh_order_no : NH174164789
     * region : 上海
     * crops : 早稻
     * price : 12.00元/亩
     * area : 500亩
     * request_time : 6/15~6/24  (9天)
     * condition : 无障碍,作物高度20-30cm
     * is_andy : 1
     * is_encase : 1
     * is_rechargeable : 1
     * is_getwater : 1
     * address : 长宁区华阳街道
     * total_price : 6000元
     */

    private int id;
    private String image_url;
    private String nh_order_no;
    private String region;
    private String crops;
    private String price;
    private String area;
    private String request_time;
    private String condition;
    private int is_andy;
    private int is_encase;
    private int is_rechargeable;
    private int is_getwater;
    private String address;
    private String total_price;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getNh_order_no() {
        return nh_order_no;
    }

    public void setNh_order_no(String nh_order_no) {
        this.nh_order_no = nh_order_no;
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
