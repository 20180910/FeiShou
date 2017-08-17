package com.zhizhong.feishou.module.my.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.github.androidtools.inter.MyOnClickListener;
import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.github.rxjava.rxbus.RxUtils;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.Constant;
import com.zhizhong.feishou.module.my.network.ApiRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscription;

/**
 * Created by administartor on 2017/8/1.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.tv_register_getcode)
    TextView tv_register_getcode;
    @BindView(R.id.et_register_phone)
    MyEditText et_register_phone;
    @BindView(R.id.et_register_code)
    EditText et_register_code;
    @BindView(R.id.et_register_pwd)
    MyEditText et_register_pwd;
    @BindView(R.id.et_register_repwd)
    MyEditText et_register_repwd;
    @BindView(R.id.tv_register_commit)
    MyTextView tv_register_commit;
    private String smsCode;
    private BottomSheetDialog xieYiDialog;
    private String agreement;

    @Override
    protected int getContentView() {
        setAppTitle("注册");
        return R.layout.act_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getXieYi(false);
    }
    private void showXieYi(String content) {
        View xieYi = LayoutInflater.from(mContext).inflate(R.layout.popu_tx_xieyi, null);
        xieYiDialog = new BottomSheetDialog(mContext);
        xieYiDialog.setCancelable(false);
        xieYiDialog.setContentView(xieYi);
        xieYiDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(xieYiDialog.isShowing()&&keyCode==KeyEvent.KEYCODE_BACK){
                    xieYiDialog.dismiss();
                    return true;
                }
                return false;
            }
        });
        xieYiDialog.show();
        xieYi.findViewById(R.id.tv_xy_queding).setOnClickListener(new MyOnClickListener() {
            @Override
            protected void onNoDoubleClick(View view) {
                xieYiDialog.dismiss();
            }
        });
        TextView xieYiContent = (TextView) xieYi.findViewById(R.id.tv_tx_xieyi_content);
        xieYiContent.setText(content);

    }
    private void getXieYi(boolean manual){
        String rnd=getRnd();
        addSubscription(ApiRequest.getRegisterXieYi(rnd,getSign("rnd",rnd)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                agreement = obj.getFly_first_agreement();
                if(manual){
                    showXieYi(agreement);
                }
            }
        }));
    }
    @OnClick({R.id.tv_register_commit,R.id.tv_register_getcode,R.id.tv_register_login,R.id.tv_register_xy})
    protected void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register_xy:
                if(TextUtils.isEmpty(agreement)){
                    showLoading();
                    getXieYi(true);
                }else{
                    showXieYi(agreement);
                }
                break;
            case R.id.tv_register_login:
                finish();
                break;
            case R.id.tv_register_getcode:
                if(TextUtils.isEmpty(getSStr(et_register_phone))){
                    showMsg("手机号不能为空");
                    return;
                }else if(!GetSign.isMobile(getSStr(et_register_phone))){
                    showMsg("请输入正确手机号");
                    return;
                }
                getMsgCode();
                break;
            case R.id.tv_register_commit:
                String phone=getSStr(et_register_phone);
                String code=getSStr(et_register_code);
                String pwd=getSStr(et_register_pwd);
                String rePwd=getSStr(et_register_repwd);

                if(TextUtils.isEmpty(getSStr(et_register_phone))){
                    showMsg("手机号不能为空");
                    return;
                }else if(!GetSign.isMobile(getSStr(et_register_phone))){
                    showMsg("请输入正确手机号");
                    return;
                }else if(TextUtils.isEmpty(smsCode)||TextUtils.isEmpty(code)||!code.equals(smsCode)){
                    showMsg("请输入正确验证码");
                    return;
                }else if(TextUtils.isEmpty(pwd)){
                    showMsg("密码不能为空");
                    return;
                }else if(!pwd.equals(rePwd)){
                    showMsg("两次密码不一样");
                    return;
                }
                register(phone,pwd);
                break;
        }
    }

    private void register(String phone, String pwd) {
        showLoading();
        Map<String,String>map=new HashMap<String,String>();
        map.put("username",phone);
        map.put("password",pwd);
        map.put("red_user_id","0");
        map.put("sign",GetSign.getSign(map));
        addSubscription(ApiRequest.register(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                Intent intent=new Intent();
                intent.putExtra(Constant.IParam.phone,phone);
                setResult(RESULT_OK,intent);
                finish();
            }
        }));
    }


    private void getMsgCode() {
        showLoading();
        Map<String, String> map = new HashMap<String, String>();
        map.put("mobile",getSStr(et_register_phone));
        map.put("rnd",getRnd());
        String sign = GetSign.getSign(map);
        map.put("sign", sign);
        showLoading();
        addSubscription(ApiRequest.getMsgCode(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj item) {
                smsCode = item.getSMSCode();
                countDown();
            }
        }));
    }

    @Override
    public void again() {

    }

    private void countDown() {
        tv_register_getcode.setEnabled(false);
        final long count = 30;
        Subscription subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .take(31)//计时次数
                .map(integer -> count - integer)
                .compose(RxUtils.appSchedulers())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        tv_register_getcode.setEnabled(true);
                        tv_register_getcode.setText("获取验证码");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        tv_register_getcode.setText("剩下" + aLong + "s");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
        addSubscription(subscribe);
    }

}
