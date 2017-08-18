package com.zhizhong.feishou.module.my.activity;

import android.Manifest;
import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.IOCallBack;
import com.github.baseclass.view.pickerview.OptionsPopupWindow;
import com.github.customview.MyCheckBox;
import com.github.customview.MyEditText;
import com.github.customview.MyRadioButton;
import com.github.customview.MyTextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.bean.CityBean;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.request.UploadImgItem;
import com.zhizhong.feishou.tools.BitmapUtils;
import com.zhizhong.feishou.tools.ImageUtils;
import com.zhizhong.feishou.tools.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by administartor on 2017/8/1.
 */

public class RealNameAuthActivity extends BaseActivity {

    @BindView(R.id.et_auth_name)
    MyEditText et_auth_name;
    @BindView(R.id.et_auth_idcard)
    MyEditText et_auth_idcard;
    @BindView(R.id.tv_auth_area)
    TextView tv_auth_area;
    @BindView(R.id.et_auth_address)
    MyEditText et_auth_address;
    @BindView(R.id.rb_auth_wrj)
    MyRadioButton rb_auth_wrj;
    @BindView(R.id.rb_auth_jz)
    MyRadioButton rb_auth_jz;
    @BindView(R.id.iv_auth_img1)
    ImageView iv_auth_img1;
    @BindView(R.id.fl_auth_img1)
    FrameLayout fl_auth_img1;
    @BindView(R.id.iv_auth_img2)
    ImageView iv_auth_img2;
    @BindView(R.id.fl_auth_img2)
    FrameLayout fl_auth_img2;
    @BindView(R.id.cb_auth_agree)
    MyCheckBox cb_auth_agree;
    @BindView(R.id.tv_auth_xy)
    TextView tv_auth_xy;
    @BindView(R.id.tv_real_name_commit)
    MyTextView tv_real_name_commit;
    private String agreement;
    private BottomSheetDialog xieYiDialog;
    private BottomSheetDialog selectPhotoDialog;

    private String imgUrlOne="";
    private String imgUrlSecond="";
    private int imgFlag=1;

    private OptionsPopupWindow pwOptions;
    private ArrayList<String> options1Items;
    private ArrayList<ArrayList<String>> options2Items;
    private ArrayList<ArrayList<ArrayList<String>>> options3Items;
    @Override
    protected int getContentView() {
        setAppTitle("实名认证");
        return R.layout.act_real_name_auth;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        getXieYi(false);
    }

    @OnClick({R.id.tv_auth_area, R.id.fl_auth_img1, R.id.fl_auth_img2, R.id.tv_auth_xy, R.id.tv_real_name_commit})
    protected void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_auth_area:
                selectArea();
                break;
            case R.id.fl_auth_img1:
                imgFlag=1;
                PhoneUtils.hiddenKeyBoard(mContext);
                showSelectPhotoDialog();
                break;
            case R.id.fl_auth_img2:
                imgFlag=2;
                PhoneUtils.hiddenKeyBoard(mContext);
                showSelectPhotoDialog();
                break;
            case R.id.tv_auth_xy:
                if (TextUtils.isEmpty(agreement)) {
                    showLoading();
                    getXieYi(true);
                } else {
                    showXieYi(agreement);
                }
                break;
            case R.id.tv_real_name_commit:
                String name=getSStr(et_auth_name);
                String idCard=getSStr(et_auth_idcard);
                String area=getSStr(tv_auth_area);
                String address=getSStr(et_auth_address);
                boolean wrj = rb_auth_wrj.isChecked();
                boolean jz = rb_auth_jz.isChecked();
                boolean agree = cb_auth_agree.isChecked();
                if(TextUtils.isEmpty(name)){
                    showMsg("请输入真实姓名");
                    return;
                }else if(TextUtils.isEmpty(idCard)){
                    showMsg("请输入身份证号");
                    return;
                }else if(TextUtils.isEmpty(area)){
                    showMsg("请选择所在地区");
                    return;
                }else if(TextUtils.isEmpty(address)){
                    showMsg("请输入地址");
                    return;
                }else if(TextUtils.isEmpty(imgUrlOne)){
                    showMsg("请上传身份证正面照");
                    return;
                }else if(TextUtils.isEmpty(imgUrlSecond)){
                    showMsg("请上传身份证反面照");
                    return;
                }else if(!agree){
                    showMsg("请同意协议");
                    return;
                }
                authCommit(name,idCard,area,address,wrj,jz);
                break;
        }
    }

    private void authCommit(String name, String idCard, String area, String address, boolean wrj, boolean jz) {
        String[] areaSplit = area.split(",");
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("real_name",name);
        map.put("card_id",idCard);
        map.put("province",areaSplit[0]);
        map.put("city",areaSplit[1]);
        map.put("area",areaSplit[2]);
        map.put("addr",address);
        map.put("have_fly",wrj?"1":"0");
        map.put("have_license",jz?"1":"0");
        map.put("card_front_img",imgUrlOne);
        map.put("card_back_img",imgUrlSecond);
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.authCommit(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                finish();
            }
        }));
    }


    private void showSelectPhotoDialog() {
        if (selectPhotoDialog == null) {
            View sexView= LayoutInflater.from(mContext).inflate(R.layout.popu_select_photo,null);
            sexView.findViewById(R.id.tv_select_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    selectPhoto();
                }
            });
            sexView.findViewById(R.id.tv_take_photo).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                    takePhoto();
                }
            });
            sexView.findViewById(R.id.tv_photo_cancle).setOnClickListener(new MyOnClickListener() {
                @Override
                protected void onNoDoubleClick(View view) {
                    selectPhotoDialog.dismiss();
                }
            });
            selectPhotoDialog = new BottomSheetDialog(mContext);
            selectPhotoDialog.setCanceledOnTouchOutside(true);
            selectPhotoDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            selectPhotoDialog.setContentView(sexView);
        }
        selectPhotoDialog.show();
    }
    //选择相册
    private void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3000);
    }
    private String path = Environment.getExternalStorageDirectory() +
            File.separator + Environment.DIRECTORY_DCIM + File.separator;
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return "IMG_" + dateFormat.format(date);
    }
    Uri photoUri;
    private String imgSaveName="";
    //拍照
    private void takePhoto() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            String state = Environment.getExternalStorageState();
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdir();
                }
                String fileName = getPhotoFileName() + ".jpg";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imgSaveName = path + fileName;
                photoUri = Uri.fromFile(new File(imgSaveName));
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, 2000);
            }
        }
    }
    private void uploadImg() {
        showLoading();
        Log.i("========","========"+imgSaveName);
        RXStart(new IOCallBack<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String newPath= ImageUtils.filePath;
                String name=ImageUtils.fileName;
                String smallBitmapPath = ImageUtils.getSmallBitmap(imgSaveName, newPath, name);
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(smallBitmapPath);
                    Bitmap bitmap  = BitmapFactory.decodeStream(fis);
                    String imgStr = BitmapUtils.bitmapToString(bitmap);
                    subscriber.onNext(imgStr);
                    subscriber.onCompleted();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
            @Override
            public void onMyNext(String baseImg) {
                UploadImgItem item=new UploadImgItem();
                item.setFile(baseImg);
                String rnd = getRnd();
                addSubscription(ApiRequest.uploadImg(rnd,getSign("rnd",rnd),item).subscribe(new MySub<BaseObj>(mContext) {
                    @Override
                    public void onMyNext(BaseObj obj) {
                        if(imgFlag==1){
                            imgUrlOne = obj.getImg();
                            iv_auth_img1.setVisibility(View.VISIBLE);
                            Glide.with(mContext).load(imgSaveName).into(iv_auth_img1);
                        }else{
                            imgUrlSecond = obj.getImg();
                            iv_auth_img2.setVisibility(View.VISIBLE);
                            Glide.with(mContext).load(imgSaveName).into(iv_auth_img2);
                        }
                    }
                }));
            }
            @Override
            public void onMyError(Throwable e) {
                super.onMyError(e);
                dismissLoading();
                showToastS("图片处理失败");
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }
        switch (requestCode){
            case 2000:
                uploadImg();
                break;
            case 3000:
                Uri uri = data.getData();
                Cursor cursor = getContentResolver().query(uri, null, null, null,null);
                if (cursor != null && cursor.moveToFirst()) {
                    imgSaveName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                    uploadImg();
                }
                break;
        }
    }
    private void selectArea() {
        PhoneUtils.hiddenKeyBoard(mContext);
        showLoading();
        RXStart(new IOCallBack<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                initAddressDialog();
                subscriber.onNext(null);
                subscriber.onCompleted();
            }
            @Override
            public void onMyNext(String s) {
                dismissLoading();
                pwOptions = new OptionsPopupWindow(mContext, "选择地区");
                // 三级联动效果
                pwOptions.setPicker(options1Items, options2Items, options3Items, true);
                // 设置默认选中的三级项目
                pwOptions.setSelectOptions(0, 0, 0);
                // 监听确定选择按钮
                pwOptions.setOnoptionsSelectListener((options1, option2, options3) -> {
                    // 返回的分别是三个级别的选中位置
                    String area = options1Items.get(options1) + ","
                            + options2Items.get(options1).get(option2) + ","
                            + options3Items.get(options1).get(option2).get(options3);
                    tv_auth_area.setText(area);
                });
                pwOptions.showAtLocation(tv_auth_area, Gravity.BOTTOM, ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
            }
        });
    }
    private void initAddressDialog() {
        options1Items = new ArrayList<String>();
        options2Items = new ArrayList<ArrayList<String>>();
        options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
        String areaJson = StreamUtils.get(this, R.raw.area);
        province(areaJson);
        // 地址选择器
    }
    private void province(String strJson) {
        List<CityBean> cityBean = new Gson().fromJson(strJson,
                new TypeToken<List<CityBean>>() {
                }.getType());
        ArrayList<String> item2;
        ArrayList<ArrayList<String>> item3;
        for (int i = 0; i < cityBean.size(); i++) {
            CityBean city=cityBean.get(i);
            options1Items.add(city.getTitle());
            item2=new ArrayList<>();
            item3=new ArrayList<ArrayList<String>>();
            for (int j = 0; j < city.getClass_list().size(); j++) {
                CityBean citySecond=city.getClass_list().get(j);
                item2.add(citySecond.getTitle());
                ArrayList<String> lastItem = new ArrayList<String>();
                for (int k = 0; k < citySecond.getClass_list().size(); k++) {
                    CityBean cityThird=citySecond.getClass_list().get(k);
                    lastItem.add(cityThird.getTitle());
                }
                item3.add(lastItem);
            }
            options2Items.add(item2);
            options3Items.add(item3);
        }
    }
    private void showXieYi(String content) {
        View xieYi = LayoutInflater.from(mContext).inflate(R.layout.popu_tx_xieyi, null);
        xieYiDialog = new BottomSheetDialog(mContext);
        xieYiDialog.setCancelable(false);
        xieYiDialog.setContentView(xieYi);
        xieYiDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (xieYiDialog.isShowing() && keyCode == KeyEvent.KEYCODE_BACK) {
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
    @Override
    public void onBackPressed() {
        if(pwOptions!=null&&pwOptions.isShowing()){
            pwOptions.dismiss();
            return;
        }
        super.onBackPressed();
    }
    @Override
    public void again() {

    }

    private void getXieYi(boolean manual) {
        String rnd = getRnd();
        addSubscription(ApiRequest.getAuthXieYi(rnd, getSign("rnd", rnd)).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                agreement = obj.getAgreement();
                if (manual) {
                    showXieYi(agreement);
                }
            }
        }));
    }


}
