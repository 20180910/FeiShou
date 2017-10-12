package com.zhizhong.feishou.module.zengzhi.network.response;

/**
 * Created by administartor on 2017/10/12.
 */

public class ZhuanYePeiXunObj {
    /**
     * id : 16
     * image_url : http://121.40.186.118:5009/upload/201709/18/201709181533442953.png
     * title : 掌握飞防植保的规范流程
     * training_teacher : 魏老师
     * add_time : 2017-10-12 11:04:06
     */

    private int id;
    private String image_url;
    private String title;
    private String training_teacher;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTraining_teacher() {
        return training_teacher;
    }

    public void setTraining_teacher(String training_teacher) {
        this.training_teacher = training_teacher;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }
}
