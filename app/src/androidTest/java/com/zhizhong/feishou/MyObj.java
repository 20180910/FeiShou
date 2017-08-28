package com.zhizhong.feishou;

import java.util.List;

/**
 * Created by administartor on 2017/8/28.
 */

public class MyObj {
    /**
     * ErrCode : 0
     * ErrMsg :
     * Response : [{"bank_id":1,"image_url":"http://121.40.186.118:5009/upload/7.png","bank_name":"中国银行"},{"bank_id":2,"image_url":"http://121.40.186.118:5009/upload/5.png","bank_name":"上海银行"},{"bank_id":3,"image_url":"http://121.40.186.118:5009/upload/6.png","bank_name":"工商银行"},{"bank_id":4,"image_url":"http://121.40.186.118:5009/upload/5.png","bank_name":"农业银行"},{"bank_id":5,"image_url":"http://121.40.186.118:5009/upload/8.png","bank_name":"建设银行"}]
     */

    private int ErrCode;
    private String ErrMsg;
    private List<ResponseBean> Response;

    public int getErrCode() {
        return ErrCode;
    }

    public void setErrCode(int ErrCode) {
        this.ErrCode = ErrCode;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String ErrMsg) {
        this.ErrMsg = ErrMsg;
    }

    public List<ResponseBean> getResponse() {
        return Response;
    }

    public void setResponse(List<ResponseBean> Response) {
        this.Response = Response;
    }

    public static class ResponseBean {
        /**
         * bank_id : 1
         * image_url : http://121.40.186.118:5009/upload/7.png
         * bank_name : 中国银行
         */

        private int bank_id;
        private String image_url;
        private String bank_name;

        public int getBank_id() {
            return bank_id;
        }

        public void setBank_id(int bank_id) {
            this.bank_id = bank_id;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }
    }
}
