package com.zhizhong.feishou.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zhizhong.feishou.Config;

/**
 * Created by administartor on 2017/8/10.
 */

public class MyOperationBro extends BroadcastReceiver {
    public interface LoginBroInter{
       void loginSuccess();
       void exitLogin();
       void addHomeworkSuccess();
    }

    private LoginBroInter inter;
    public MyOperationBro(LoginBroInter inter) {
        this.inter=inter;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        int flag = intent.getIntExtra(Config.Bro.flag, 0);
        switch (flag){
            case Config.Bro.exit_login:
                inter.exitLogin();
            break;
            case 0:
                inter.loginSuccess();
            break;
            case Config.Bro.addHomeworkSuccess:
                inter.addHomeworkSuccess();
            break;
        }
    }
}
