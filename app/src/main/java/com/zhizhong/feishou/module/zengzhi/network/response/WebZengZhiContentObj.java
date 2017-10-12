package com.zhizhong.feishou.module.zengzhi.network.response;

import com.zhizhong.feishou.base.BaseObj;

/**
 * Created by administartor on 2017/10/12.
 */

public class WebZengZhiContentObj extends BaseObj {
    /**
     * id : 16
     * content :
     */

    private int id;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
