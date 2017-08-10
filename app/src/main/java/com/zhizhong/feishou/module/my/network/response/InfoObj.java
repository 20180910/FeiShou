package com.zhizhong.feishou.module.my.network.response;

import com.zhizhong.feishou.base.BaseObj;

/**
 * Created by administartor on 2017/8/10.
 */

public class InfoObj extends BaseObj {

    /**
     * user_id : 7
     * nick_name : 1哟嚯
     * avatar : http://121.40.186.118:5009/upload/201708/10/17081016382242902039.jpg
     * birthday : 2017-8-4
     * sex : 男
     * user_type : 1
     */

    private String user_id;
    private String nick_name;
    private String avatar;
    private String birthday;
    private String sex;
    private int user_type;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }
}
