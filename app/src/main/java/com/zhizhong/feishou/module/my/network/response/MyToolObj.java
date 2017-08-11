package com.zhizhong.feishou.module.my.network.response;

/**
 * Created by administartor on 2017/8/11.
 */

public class MyToolObj {

    /**
     * id : 3
     * userid : 1
     * image_url : http://121.40.186.118:5009/upload/123.jpg
     * product_name : 莲花无人机
     * product_model : 国产-歼20
     * product_number : 7
     * manufacturer : 中国航天航空
     * add_time : 2017-08-07 17:53:29
     */

    private int id;
    private int userid;
    private String image_url;
    private String product_name;
    private String product_model;
    private int product_number;
    private String manufacturer;
    private String add_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_model() {
        return product_model;
    }

    public void setProduct_model(String product_model) {
        this.product_model = product_model;
    }

    public int getProduct_number() {
        return product_number;
    }

    public void setProduct_number(int product_number) {
        this.product_number = product_number;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
