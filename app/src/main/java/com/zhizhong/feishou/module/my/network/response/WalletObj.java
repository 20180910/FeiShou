package com.zhizhong.feishou.module.my.network.response;

import com.zhizhong.feishou.base.BaseObj;

import java.util.List;

/**
 * Created by administartor on 2017/8/10.
 */

public class WalletObj extends BaseObj {

    /**
     * user_money : 29998
     * user_money_log : [{"add_time":"2017-08-10 10:38:10","remark":"提现","value":-2,"moneyType":"-"},{"add_time":"2017-06-27 11:20:03","remark":"提现成功！","value":-100,"moneyType":"-"},{"add_time":"2017-06-27 11:15:50","remark":"提现成功！","value":-100,"moneyType":"-"},{"add_time":"2017-06-27 11:15:03","remark":"提现成功！","value":-100,"moneyType":"-"},{"add_time":"2017-06-27 11:14:40","remark":"提现成功！","value":-100,"moneyType":"-"},{"add_time":"2017-06-05 11:16:58","remark":"订单号：N201705311507108076","value":300,"moneyType":"+"},{"add_time":"2017-06-05 11:16:58","remark":"订单号：N201705311507108076","value":100,"moneyType":"+"},{"add_time":"2017-06-01 16:14:16","remark":"订单号：N201706011402020719","value":400,"moneyType":"+"},{"add_time":"2017-05-31 17:57:23","remark":"订单号：N201705311746008842","value":400,"moneyType":"+"},{"add_time":"2017-05-31 16:55:59","remark":"订单号：N201705311648391017","value":400,"moneyType":"+"},{"add_time":"2017-05-31 16:20:36","remark":"订单号：N201705311617185584","value":100,"moneyType":"+"}]
     */

    private double user_money;
    private List<UserMoneyLogBean> user_money_log;

    public double getUser_money() {
        return user_money;
    }

    public void setUser_money(int user_money) {
        this.user_money = user_money;
    }

    public List<UserMoneyLogBean> getUser_money_log() {
        return user_money_log;
    }

    public void setUser_money_log(List<UserMoneyLogBean> user_money_log) {
        this.user_money_log = user_money_log;
    }

    public static class UserMoneyLogBean {
        /**
         * add_time : 2017-08-10 10:38:10
         * remark : 提现
         * value : -2
         * moneyType : -
         */

        private String add_time;
        private String remark;
        private double value;
        private String moneyType;

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public double getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getMoneyType() {
            return moneyType;
        }

        public void setMoneyType(String moneyType) {
            this.moneyType = moneyType;
        }
    }
}
