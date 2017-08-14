package com.zhizhong.feishou.module.my.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.Constant;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.response.AccountObj;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by administartor on 2017/8/2.
 */

public class TiXianActivity extends BaseActivity {
    @BindView(R.id.ll_tixian)
    LinearLayout ll_tixian;
    @BindView(R.id.tv_tx_accout)
    TextView tv_tx_accout;
    @BindView(R.id.tv_tx_commit)
    TextView tv_tx_commit;
    @BindView(R.id.iv_tx_bank)
    ImageView iv_tx_bank;
    @BindView(R.id.et_tx_money)
    EditText et_tx_money;

    private String accountId;
    @Override
    public void again() {

    }

    @Override
    protected int getContentView() {
        setAppTitle("提现");
        return R.layout.act_ti_xian;
    }

    @Override
    protected void initView() {
        et_tx_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int selectionStart = et_tx_money.getSelectionStart();
                int selectionEnd = et_tx_money.getSelectionEnd();
                if (!isOnlyPointNumber(et_tx_money.getText().toString())){
                    s.delete(selectionStart - 1, selectionEnd);
                }
            }
        });
    }
    public boolean isOnlyPointNumber(String number) {//保留两位小数正则
        if(number.indexOf(".")==-1){
            return true;
        }else if(number.indexOf(".")==0){
            number="0"+number;
        }
        Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,2}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }
    @Override
    protected void initData() {
        getDefaultAccount();
    }

    private void getDefaultAccount() {
        showLoading();
        addSubscription(ApiRequest.getDefaultAccount(getUserId(),getSign()).subscribe(new MySub<List<AccountObj>>(mContext) {
            @Override
            public void onMyNext(List<AccountObj> list) {
                AccountObj account = list.get(0);
                accountId=account.getId()+"";
                tv_tx_accout.setText(account.getBank_name());
                Glide.with(mContext).load(account.getBank_image()).error(R.color.c_press).into(iv_tx_bank);
            }
        }));
    }

    @OnClick({R.id.ll_tixian,R.id.tv_tx_commit,R.id.tv_tx_all})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.tv_tx_all:
                getAllMoney();
            break;
            case R.id.ll_tixian:
                STActivityForResult(AccountListActivity.class,100);
            break;
            case R.id.tv_tx_commit:
                String money = getSStr(et_tx_money);
                if(TextUtils.isEmpty(getSStr(tv_tx_accout))){
                    showMsg("请选择到账账户");
                    return;
                }else if(money.length()==1&&money.indexOf(".")==0){
                    showMsg("请输入金额");
                    return;
                }
                if(money.indexOf(".")==0){
                    money="0"+money;
                }
                if(money.indexOf(".")==money.length()-1){
                    money=money.replace(".","");
                }
                double resultMoney = Double.parseDouble(money);
                if(resultMoney<=0){
                    showMsg("请输入金额");
                    return;
                }
                TiXian(resultMoney);
            break;
        }
    }
    private void TiXian(double money) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("account_id",accountId);
        map.put("amount",money+"");
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.tiXian(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                finish();
            }
        }));
    }
    private void getAllMoney() {
        showLoading();
        addSubscription(ApiRequest.getAllMoney(getUserId(),getSign()).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                et_tx_money.setText(obj.getAllmoney()+"");
            }
        }));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch (requestCode){
                case 100:
                    AccountObj account = (AccountObj) data.getSerializableExtra(Constant.IParam.account);
                    accountId=account.getId()+"";
                    tv_tx_accout.setText(account.getBank_card());
                    Glide.with(mContext).load(account.getBank_image()).error(R.color.c_press).into(iv_tx_bank);
                    break;
            }
        }
    }
}
