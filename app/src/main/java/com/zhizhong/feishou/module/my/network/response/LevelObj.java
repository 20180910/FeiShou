package com.zhizhong.feishou.module.my.network.response;

import com.zhizhong.feishou.base.BaseObj;

/**
 * Created by administartor on 2017/8/10.
 */

public class LevelObj extends BaseObj {

    /**
     * user_level : 0
     * content : 等级评判规则等级评判规则等级评判规则等级评判规则
     */

    private int user_level;
    private String content;

    public int getUser_level() {
        return user_level;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
