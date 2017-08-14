package com.zhizhong.feishou.module.zengzhi.network.response;

/**
 * Created by administartor on 2017/8/11.
 */

public class ZengZhiObj {

    /**
     * id : 7
     * image_url : http://121.40.186.118:5009/upload/123.jpg
     * product_name : 多轴农用无人机
     * product_model : 自动版10轴5kg
     * price : 16000
     * add_time : 2017-08-08 10:56:14
     */

    private int id;
    private String image_url;
    private String product_name;
    private String product_model;
    private double price;
    private String add_time;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
