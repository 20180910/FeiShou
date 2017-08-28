package com.zhizhong.feishou;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.github.retrofitutil.NetWorkManager;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.zhizhong.feishou", appContext.getPackageName());
    }
    @Test
    public void asfd() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        NetWorkManager.getInstance(appContext,"http://121.40.186.118:5009/",BuildConfig.DEBUG).complete();

        ApiRequest.getList(new Callback<MyObj>() {
            @Override
            public void onResponse(Call<MyObj> call, Response<MyObj> response) {
                Log.i("====","==="+response.isSuccessful());
                response.body().toString();
                Log.i("====","==="+response.body().toString());

            }

            @Override
            public void onFailure(Call<MyObj> call, Throwable t) {
                Log.i("====","===onFailure"+t.getMessage());

            }
        });
    }
    public static class ApiRequest{
        public static IRequest getClient(){
            return NetWorkManager.getGeneralClient().create(IRequest.class);
//            return NetWorkManager.getGeneralStringClient().create(IRequest.class);
        }
        public static void getList(Callback callBack){
              getClient().getList("1", "1cd92815f64aa33b70317909e0aadd13").enqueue(callBack);
        }
    }
    public interface IRequest{
        @GET("api/Farmer/GetBankList")
        Call<MyObj> getList(@Query("rnd") String rnd,@Query("sign") String sign);
    }
    @Test
    public void afds() throws Exception {
        PoiSearch poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if(poiResult==null){
                    return;
                }
                List<PoiInfo> allPoi = poiResult.getAllPoi();
                for (int i = 0; i <allPoi.size(); i++) {
                    System.out.println(allPoi.get(i).name);
                    System.out.println(allPoi.get(i).address);
                    System.out.println("=====================");
                }
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }

            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

            }
        });
        poiSearch.searchInCity((new PoiCitySearchOption())
                .city("上海")
                .keyword("兆丰")
                .pageNum(10));
    }
}
