package com.zhizhong.feishou.module.my.network.response;

import com.zhizhong.feishou.base.BaseObj;

/**
 * Created by administartor on 2017/8/29.
 */

public class AuthObj extends BaseObj{
    /**
     * id : 0
     * user_id : 2
     * real_name :
     * card_id :
     * province :
     * city :
     * area :
     * addr :
     * have_fly : 0
     * have_license : 0
     * card_front_img :
     * card_back_img :
     * addtime :
     * is_validation : 0
     */

    private int id;
    private int user_id;
    private String real_name;
    private String card_id;
    private String province;
    private String city;
    private String area;
    private String addr;
    private int have_fly;
    private int have_license;
    private String card_front_img;
    private String card_back_img;
    private String addtime;
    private int is_validation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getHave_fly() {
        return have_fly;
    }

    public void setHave_fly(int have_fly) {
        this.have_fly = have_fly;
    }

    public int getHave_license() {
        return have_license;
    }

    public void setHave_license(int have_license) {
        this.have_license = have_license;
    }

    public String getCard_front_img() {
        return card_front_img;
    }

    public void setCard_front_img(String card_front_img) {
        this.card_front_img = card_front_img;
    }

    public String getCard_back_img() {
        return card_back_img;
    }

    public void setCard_back_img(String card_back_img) {
        this.card_back_img = card_back_img;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getIs_validation() {
        return is_validation;
    }

    public void setIs_validation(int is_validation) {
        this.is_validation = is_validation;
    }
}
