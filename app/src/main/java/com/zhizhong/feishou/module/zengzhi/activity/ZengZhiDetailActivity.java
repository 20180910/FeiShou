package com.zhizhong.feishou.module.zengzhi.activity;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zhizhong.feishou.GetSign;
import com.zhizhong.feishou.R;
import com.zhizhong.feishou.base.BaseActivity;
import com.zhizhong.feishou.base.MySub;
import com.zhizhong.feishou.module.zengzhi.Constant;
import com.zhizhong.feishou.module.zengzhi.network.ApiRequest;
import com.zhizhong.feishou.module.zengzhi.network.response.WebZengZhiContentObj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by administartor on 2017/10/12.
 */

public class ZengZhiDetailActivity extends BaseActivity {
    @BindView(R.id.wv_zz_detail)
    WebView webview;
    private String id;
    private int type;

    @Override
    public void again() {
        initData();
    }
    @Override
    protected int getContentView() {
        return R.layout.act_zeng_zhi_detail;
    }
    @Override
    protected void initView() {
        type = getIntent().getIntExtra(Constant.IParam.zz_detail_type,1);
        if(type==1){
            setAppTitle("设备详情");
        }else{
            setAppTitle("培训详情");
        }
        id = getIntent().getStringExtra(Constant.IParam.id);
    }
    private void getData() {
        Map<String,String> map=new HashMap<String,String>();
//        if(Constant.IParam.zz_detail_type_0.equals(type)){
            map.put("type",type+"");//设备详情
            map.put("appreciation_service_id",id);//设备详情
            map.put("sign", GetSign.getSign(map));
            addSubscription(ApiRequest.getSheBeiDetail(map).subscribe(new MySub<WebZengZhiContentObj>(mContext,pl_load) {
                @Override
                public void onMyNext(WebZengZhiContentObj obj) {
                    setWeb(obj.getContent());
                }
            }));
        /*}else{
            map.put("professional_training_id",id);//专业培训详情
            map.put("sign", GetSign.getSign(map));
            addSubscription(ApiRequest.getPeiXunDetail(map).subscribe(new MySub<WebZengZhiContentObj>(mContext,pl_load) {
                @Override
                public void onMyNext(WebZengZhiContentObj obj) {
                    setWeb(obj.getContent());
                }
            }));
        }*/
    }

    @Override
    protected void initData() {
        showProgress();
        getData();
    }

    @Override
    protected void onViewClick(View v) {

    }
    public void setWeb(String content){
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
        //自适应屏幕  
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webview.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        //设置Web视图
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                initWebTopView();
            }
        });

        webview.loadDataWithBaseURL(null, getNewContent(content), "text/html", "utf-8",null);
//        webview.loadUrl(url);
        // 设置WevView要显示的网页
//        webview.loadDataWithBaseURL(null, content, "text/html", "utf-8",null);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setJavaScriptEnabled(true); //设置支持Javascript
        webview.requestFocus(); //触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
    }
    public String getNewContent(String htmltext){
        try {
            Document doc= Jsoup.parse(htmltext);
            Elements elements=doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width","100%").attr("height","auto");
            }
            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }
}
