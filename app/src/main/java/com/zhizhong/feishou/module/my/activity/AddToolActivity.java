package com.zhizhong.feishou.module.my.activity;

import android.Manifest;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.androidtools.PhoneUtils;
import com.github.androidtools.inter.MyOnClickListener;
import com.github.baseclass.rx.IOCallBack;
import com.github.customview.MyEditText;
import com.github.customview.MyTextView;
import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.BaseObj;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.my.network.ApiRequest;
import com.zhizhong.feishou.module.my.network.request.UploadImgItem;
import com.zhizhong.feishou.tools.BitmapUtils;
import com.zhizhong.feishou.tools.ImageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by administartor on 2017/8/3.
 */

public class AddToolActivity extends BaseActivity {
    @BindView(R.id.iv_add_tool_img)
    ImageView iv_add_tool_img;
    @BindView(R.id.fl_add_tool_img)
    FrameLayout fl_add_tool_img;
    @BindView(R.id.et_add_tool_name)
    MyEditText et_add_tool_name;
    @BindView(R.id.et_add_tool_model)
    MyEditText et_add_tool_model;
    @BindView(R.id.et_add_tool_cj)
    MyEditText et_add_tool_cj;
    @BindView(R.id.et_add_tool_num)
    MyEditText et_add_tool_num;
    @BindView(R.id.tv_add_tool_commit)
    MyTextView tv_add_tool_commit;
    private String imgUrl="";
    private BottomSheetDialog selectPhotoDialog;
    @Override
    public void again() {
        initData();
    }

    @Override
    protected int getContentView() {
        setAppTitle("添加工具");
        return R.layout.act_add_tool;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.tv_add_tool_commit,R.id.fl_add_tool_img})
    protected void onViewClick(View v) {
        switch (v.getId()){
            case R.id.fl_add_tool_img:
                PhoneUtils.hiddenKeyBoard(mContext);
                showSelectPhotoDialog();
            break;
            case R.id.tv_add_tool_commit:
                PhoneUtils.hiddenKeyBoard(mContext);
                String name=getSStr(et_add_tool_name);
                String model=getSStr(et_add_tool_model);
                String cj=getSStr(et_add_tool_cj);
                String num=getSStr(et_add_tool_num);
                if(TextUtils.isEmpty(name)){
                    showMsg("请输入产品名称");
                    return;
                }else if(TextUtils.isEmpty(model)){
                    showMsg("请输入产品型号");
                    return;
                }else if(TextUtils.isEmpty(cj)){
                    showMsg("请输入产品厂家");
                    return;
                }else if(TextUtils.isEmpty(num)){
                    showMsg("请输入产品数量");
                    return;
                }else if(TextUtils.isEmpty(imgUrl)){
                    showMsg("请上传图片");
                    return;
                }
                addTool(name,model,cj,num);
            break;
        }
    }

    private void addTool(String name,String model,String cj,String num) {
        showLoading();
        Map<String,String> map=new HashMap<String,String>();
        map.put("user_id",getUserId());
        map.put("image_url",imgUrl);
        map.put("product_name",name);
        map.put("product_model",model);
        map.put("manufacturer",cj);
        map.put("number",num);
        map.put("sign", GetSign.getSign(map));
        addSubscription(ApiRequest.addTool(map).subscribe(new MySub<BaseObj>(mContext) {
            @Override
            public void onMyNext(BaseObj obj) {
                showMsg(obj.getMsg());
                setResult(RESULT_OK);
                finish();
            }
        }));
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
                        imgUrl = obj.getImg();
                        iv_add_tool_img.setVisibility(View.VISIBLE);
                        Glide.with(mContext).load(imgSaveName).into(iv_add_tool_img);
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

}
