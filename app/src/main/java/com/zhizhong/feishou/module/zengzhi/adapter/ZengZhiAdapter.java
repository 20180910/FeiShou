package com.zhizhong.feishou.module.zengzhi.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by administartor on 2017/10/12.
 */

public class ZengZhiAdapter extends FragmentStatePagerAdapter {
    String[]title={"设备购买","专业培训"};
    List<Fragment> list;

    public void setList(List<Fragment> list) {
        this.list = list;
    }

    public ZengZhiAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
//        return super.getPageTitle(position);
    }
}
