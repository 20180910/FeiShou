package com.zhizhong.feishou.module.my.network.request;

import java.util.List;

/**
 * Created by administartor on 2017/8/11.
 */

public class HomeworkCityItem {

    private List<BodyBean> body;

    public List<BodyBean> getBody() {
        return body;
    }

    public void setBody(List<BodyBean> body) {
        this.body = body;
    }

    public static class BodyBean {
        public BodyBean(String city_name) {
            this.city_name = city_name;
        }
        /**
         * city_name : sample string 1
         */

        private String city_name;

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }
    }
}
