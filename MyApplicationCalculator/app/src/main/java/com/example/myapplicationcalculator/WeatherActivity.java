package com.example.myapplicationcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import okhttp3.HttpUrl;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class WeatherActivity extends AppCompatActivity {
    Thread thread;
    Runnable networkTask;
    private static  JsonObject returnData = null;
    private JsonObject  jsonObject_weatherNowApi,jsnObweatherNowApi_today,jsnObweatherNowApi_today_airquality;
    private JSONObject jsonObject_weather;
    HashMap hashMapView;
    Intent intent;
    private String tvcity = "";
    /*
     *今天的天气信息的key
     */
    String [] todaydate = {"date", "address","temp","temparea","weathercondition","airqualitynum","airqualitygrade"};
    String [] hourTime ={"onehourtime","twohourtime","threehourtime","fourhourtime",
            "fivehourtime","sixhourtime","sevenhourtime","eighthourtime"};
    String [] hourTemp ={"onehourtemp","twohourtemp","threehourtemp","fourhourtemp",
            "fivehourtemp","sixhourtemp","sevenhourtemp","eighthourtemp"};
    String [] hourWeatherCondition ={"onehourcondition","twohourcondition","threehourcondition","fourhourcondition",
            "fivehourcondition","sixhourcondition","sevenhourcondition","eighthourcondition"};
    /*
     *之后七天的信息的key
     */

    String [] dayDate = {"onedaydate","twodaydate","threedaydate","fourdaydate",
            "fivedaydate","sixdaydate","sevendaydate"};
    String [] dayWeek = {"onedayweek","twodayweek","threedayweek","fourdayweek",
            "fivedayweek","sixdayweek","sevendayweek"};
    String [] dayCondition = {"onedaycondition","twodaycondition","threedaycondition","fourdaycondition",
            "fivedaycondition","sixdaycondition","sevendaycondition"};
    String [] dayMaxTemp = {"onedaymaxtemp","twodaymaxtemp","threedaymaxtemp","fourdaymaxtemp",
            "fivedaymaxtemp","sixdaymaxtemp","sevendaymaxtemp"};
    String [] dayMinTemp = {"onedaymintemp","twodaymintemp","threedaymintemp","fourdaymintemp",
            "fivedaymintemp","sixdaymintemp","sevendaymintemp"};
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            Bundle data = msg.getData();
            String val = data.getString("value");
            if(val.equals("thread"))
            {
                city.setText(tvcity);
                updata("rtweather",tvcity);//小时
                updata("weather.future",tvcity);//未来七天
                updata("weather.today",tvcity);//今天
                updata("weather.pm25",tvcity);//今天中的空气质量
            }
            if(val.equals("future"))
            {
                JsonArray jsonArray=null;
                jsonArray = jsonObject_weatherNowApi.getAsJsonArray("result");
                int dayindex = 0;//天气的index,从零开始
                for(JsonElement jsonElement : jsonArray){
                    if(dayindex>=7){//大于七天返回
                        break;
                    }
                    //获得数组的单个Json对象
                    JsonObject jsonObject1 = jsonElement.getAsJsonObject();
                    System.out.println("nowapi"+jsonObject1.get("days").getAsString().substring(5));
                    hashMapView.put(dayDate[dayindex],jsonObject1.get("days").getAsString().substring(5));
                    hashMapView.put(dayWeek[dayindex],jsonObject1.get("week").getAsString());
                    String temp_low = addSpace(jsonObject1.get("temp_low").getAsString());
                    hashMapView.put(dayMinTemp[dayindex], temp_low);
                    String temp_high = addSpace(jsonObject1.get("temp_high").getAsString());
                    hashMapView.put(dayMaxTemp[dayindex],temp_high);
                    hashMapView.put(dayCondition[dayindex],jsonObject1.get("weather").getAsString());
                    dayindex++;
                }

                if(jsonObject_weatherNowApi != null){
                    tvDayAfterOneDay.setText(hashMapView.get(dayDate[0]).toString());
                    tvDayAfterOneDay_Week.setText(hashMapView.get(dayWeek[0]).toString());
                    tvDayAfterOneDay_MinTemperture.setText(hashMapView.get(dayMinTemp[0]).toString());
                    tvDayAfterOneDay_MaxTemperture.setText(hashMapView.get(dayMaxTemp[0]).toString());
                    displayiv(hashMapView.get(dayCondition[0]).toString(), ivDayAfterOneDay_ImageView);

                    tvDayAfterTwoDay.setText(hashMapView.get(dayDate[1]).toString());
                    tvDayAfterTwoDay_Week.setText(hashMapView.get(dayWeek[1]).toString());
                    tvDayAfterTwoDay_MinTemperture.setText(hashMapView.get(dayMinTemp[1]).toString());
                    tvDayAfterTwoDay_MaxTemperture.setText(hashMapView.get(dayMaxTemp[1]).toString());
                    displayiv(hashMapView.get(dayCondition[1]).toString(), ivDayAfterTwoDay_ImageView);

                    tvDayAfterThreeDay.setText(hashMapView.get(dayDate[2]).toString());
                    tvDayAfterThreeDay_Week.setText(hashMapView.get(dayWeek[2]).toString());
                    tvDayAfterThreeDay_MinTemperture.setText(hashMapView.get(dayMinTemp[2]).toString());
                    tvDayAfterThreeDay_MaxTemperture.setText(hashMapView.get(dayMaxTemp[2]).toString());
                    displayiv(hashMapView.get(dayCondition[2]).toString(), ivDayAfterThreeDay_ImageView);

                    tvDayAfterFourDay.setText(hashMapView.get(dayDate[3]).toString());
                    tvDayAfterFourDay_Week.setText(hashMapView.get(dayWeek[3]).toString());
                    tvDayAfterFourDay_MinTemperture.setText(hashMapView.get(dayMinTemp[3]).toString());
                    tvDayAfterFourDay_MaxTemperture.setText(hashMapView.get(dayMaxTemp[3]).toString());
                    displayiv(hashMapView.get(dayCondition[3]).toString(), ivDayAfterFourDay_ImageView);

                    tvDayAfterFiveDay.setText(hashMapView.get(dayDate[4]).toString());
                    tvDayAfterFiveDay_Week.setText(hashMapView.get(dayWeek[4]).toString());
                    tvDayAfterFiveDay_MinTemperture.setText(hashMapView.get(dayMinTemp[4]).toString());
                    tvDayAfterFiveDay_MaxTemperture.setText(hashMapView.get(dayMaxTemp[4]).toString());
                    displayiv(hashMapView.get(dayCondition[4]).toString(), ivDayAfterFiveDay_ImageView);

                    tvDayAfterSixDay.setText(hashMapView.get(dayDate[5]).toString());
                    tvDayAfterSixDay_Week.setText(hashMapView.get(dayWeek[5]).toString());
                    tvDayAfterSixDay_MinTemperture.setText(hashMapView.get(dayMinTemp[5]).toString());
                    tvDayAfterSixDay_MaxTemperture.setText(hashMapView.get(dayMaxTemp[5]).toString());
                    displayiv(hashMapView.get(dayCondition[5]).toString(), ivDayAfterSixDay_ImageView);
                }

                /*
                 晚上零点时获取会只有六天的信息，少了一天
                 */
                tvDayAfterSevenDay.setText(hashMapView.get(dayDate[6]).toString());
                tvDayAfterSevenDay_Week.setText(hashMapView.get(dayWeek[6]).toString());
                tvDayAfterSevenDay_MinTemperture.setText(hashMapView.get(dayMinTemp[6]).toString());
                tvDayAfterSevenDay_MaxTemperture.setText(hashMapView.get(dayMaxTemp[6]).toString());
                displayiv(hashMapView.get(dayCondition[6]).toString(), ivDayAfterSevenDay_ImageView);
            }
            if(val.equals("today"))
            {
                 /*
                今天的信息,时间，空气质量等
                 */
                JsonObject jsnObweaNowApi_today_result = jsnObweatherNowApi_today.getAsJsonObject("result");
                String days = jsnObweaNowApi_today_result.get("days").getAsString();//日期
                hashMapView.put(todaydate[0], days);
                String citynm = jsnObweaNowApi_today_result.get("citynm").getAsString();//城市名
                hashMapView.put(todaydate[1], citynm);
                String temperture_curr = jsnObweaNowApi_today_result.get("temperature_curr").getAsString();//温度区间
                hashMapView.put(todaydate[2], temperture_curr);
                String temperature = jsnObweaNowApi_today_result.get("temperature").getAsString();//当前温度
                hashMapView.put(todaydate[3], temperature);
                String weather = jsnObweaNowApi_today_result.get("weather").getAsString();//天气状况,例:晴
                hashMapView.put(todaydate[4], weather);

                tvDayToday_Date.setText(hashMapView.get(todaydate[0]).toString());
                city.setText(hashMapView.get(todaydate[1]).toString());
                displayiv(hashMapView.get(todaydate[4]).toString(), ivDayToday_ImageView);
                tvDayToday_NowTemperture.setText(hashMapView.get(todaydate[2]).toString());
                tvDayToday_ScopeTemperture.setText(hashMapView.get(todaydate[3]).toString());
                tvDayToday_WeatherChange.setText(hashMapView.get(todaydate[4]).toString());
            }
            if(val.equals("pm25"))//空气质量
            {
                JsonObject jsonObjectNowApi_today_air_quality = jsnObweatherNowApi_today_airquality.getAsJsonObject("result");
                String aqi = jsonObjectNowApi_today_air_quality.get("aqi").getAsString();//数字
                hashMapView.put(todaydate[5], aqi);
                String aqi_levnm = jsonObjectNowApi_today_air_quality.get("aqi_levnm").getAsString();//等级
                hashMapView.put(todaydate[6], aqi_levnm);

                tvDayToday_AirQualityNum.setText(hashMapView.get(todaydate[5]).toString());
                tvDayToday_AirQualityGrade.setText(hashMapView.get(todaydate[6]).toString());
            }

//            int [] image= {R.drawable.qing, R.drawable.duoyun,R.drawable.ying, R.drawable.xiaoyu,
//                    R.drawable.zhongyu,R.drawable.dayu, R.drawable.baoyu, R.drawable.dabaoyu, R.drawable.tedabaoyu,
//                    R.drawable.leizhenyu,R.drawable.qialeyu,R.drawable.xiaoyue,R.drawable.zhongyue,
//                    R.drawable.dayue,R.drawable.dabaoyue,R.drawable.longjuanfeng};
            if(val.equals("hour")){
                String datetoday = "20001010";//默认时间
                try{
                    JSONObject result = jsonObject_weather.getJSONObject("Result");
                    datetoday = result.get("Todate").toString();
//                    String updateTimeString = result.get("UpdateTime").toString().substring(0,2);
                    JSONObject weather24th = result.getJSONObject("Weather24h");
                    JSONObject jsondate = weather24th.getJSONObject("date_"+datetoday);
                    Iterator<String> iterable = jsondate.keys();
                    int viewNum=0;//视图的index
                    while (iterable.hasNext() && viewNum<8){//有今天的小时的JSON对象并且获得小时的数据小于8个
                        // ，显示视图一共有八个，等于8的时候说明已经获得八个小时，所以跳出循环
                        String skey = iterable.next();
                        System.out.println("Weatherhhh.java  "+skey);
                        JSONObject jsonObject = jsondate.getJSONObject(skey);
                        hashMapView.put(hourTime[viewNum], skey.substring(5)+":00");//小时数据添加到hashMapView里
                        String temp = jsonObject.get("Temp").toString();//添加温度
                        System.out.println(temp);
                        hashMapView.put(hourTemp[viewNum], temp);
                        String weatherhour = jsonObject.get("Weather").toString();
                        hashMapView.put(hourWeatherCondition[viewNum], weatherhour);//添加天气状况,比如:晴
                        viewNum++;//迭代一个小时就加一
                    }
                    if(viewNum<8){//第一天获得小时数据不到八个，需要获得第二天的
                        JSONObject weather24thTomorrow = weather24th.getJSONObject("date_"+getTomorray(datetoday));
                        System.out.println(getTomorray(datetoday));
                        Iterator<String> iterable2 = weather24thTomorrow.keys();//迭代器，先获得Json的key，数据不稳定且不确定
                                                        // 根据key来判断,如果有数据才执行，没有就跳出
                        while (iterable2.hasNext() && viewNum<8){//迭代小时,viewNum==8说明不需要再获得数据
                            String skey = iterable2.next();
                            System.out.println("Weatherhhh2.java  "+skey);
                            JSONObject jsonObject = weather24thTomorrow.getJSONObject(skey);//小时
                            hashMapView.put(hourTime[viewNum], skey.substring(5)+":00");
                            String temp = jsonObject.get("Temp").toString();
                            System.out.println(temp);
                            hashMapView.put(hourTemp[viewNum], temp);
                            String weatherhour = jsonObject.get("Weather").toString();
                            hashMapView.put(hourWeatherCondition[viewNum], weatherhour);
                            viewNum++;
                        }
                    }
                }catch (JSONException e){
                    System.out.println(e);
                }

                try{//将yyyyMMdd形式改成yyyy-MM-dd形式
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    formatter.setLenient(false);
                    Date newDate= formatter.parse(datetoday);
                    formatter = new SimpleDateFormat("yyyy-MM-dd");
                    hashMapView.put("todaydate",formatter.format(newDate));
                }catch (ParseException e){
                    System.out.println(e);
                }

                tvHourOneTime.setText(hashMapView.get(hourTime[0]).toString());
                tvHourOneTemperture.setText(hashMapView.get(hourTemp[0]).toString());
                //根据获得的天气状况显示指定的天气图标
                displayiv(hashMapView.get(hourWeatherCondition[0]).toString(), ivHourOneImage);

                tvHourTwoTime.setText(hashMapView.get(hourTime[1]).toString());
                tvHourTwoTemperture.setText(hashMapView.get(hourTemp[1]).toString());
                displayiv(hashMapView.get(hourWeatherCondition[1]).toString(), ivHourTwoImage);

                tvHourThreeTime.setText(hashMapView.get(hourTime[2]).toString());
                tvHourThreeTemperture.setText(hashMapView.get(hourTemp[2]).toString());
                displayiv(hashMapView.get(hourWeatherCondition[2]).toString(), ivHourThreeImage);

                tvHourFourTime.setText(hashMapView.get(hourTime[3]).toString());
                tvHourFourTemperture.setText(hashMapView.get(hourTemp[3]).toString());
                displayiv(hashMapView.get(hourWeatherCondition[3]).toString(), ivHourFourImage);

                tvHourFiveTime.setText(hashMapView.get(hourTime[4]).toString());
                tvHourFiveTemperture.setText(hashMapView.get(hourTemp[4]).toString());
                displayiv(hashMapView.get(hourWeatherCondition[4]).toString(), ivHourFiveImage);

                tvHourSixTime.setText(hashMapView.get(hourTime[5]).toString());
                tvHourSixTemperture.setText(hashMapView.get(hourTemp[5]).toString());
                displayiv(hashMapView.get(hourWeatherCondition[5]).toString(), ivHourSixImage);

                tvHourSevenTime.setText(hashMapView.get(hourTime[6]).toString());
                tvHourSevenTemperture.setText(hashMapView.get(hourTemp[6]).toString());
                displayiv(hashMapView.get(hourWeatherCondition[6]).toString(), ivHourSevenImage);

                tvHourEightTime.setText(hashMapView.get(hourTime[7]).toString());
                tvHourEightTemperture.setText(hashMapView.get(hourTemp[7]).toString());
                displayiv(hashMapView.get(hourWeatherCondition[7]).toString(), ivHourEightImage);
            }
        }
    };
    FloatingActionButton fabAddCity;
    EditText city;
    ImageButton nowLocation, reGet;
    //Day天气图标
    ImageView ivDayToday_ImageView, ivDayAfterOneDay_ImageView, ivDayAfterTwoDay_ImageView, ivDayAfterThreeDay_ImageView, ivDayAfterFourDay_ImageView,
            ivDayAfterFiveDay_ImageView, ivDayAfterSixDay_ImageView, ivDayAfterSevenDay_ImageView;
    //小时天气图标,逐三小时报
    ImageView ivHourOneImage, ivHourTwoImage, ivHourThreeImage, ivHourFourImage, ivHourFiveImage, ivHourSixImage,
            ivHourSevenImage, ivHourEightImage;
    //当天
    TextView tvDayToday_Date, tvDayToday_NowTemperture, tvDayToday_ScopeTemperture,
            tvDayToday_WeatherChange, tvDayToday_AirQualityNum, tvDayToday_AirQualityGrade;
    TextView tvDayAfterOneDay, tvDayAfterOneDay_Week, tvDayAfterOneDay_MaxTemperture, tvDayAfterOneDay_MinTemperture;
    //之后第二天
    TextView tvDayAfterTwoDay, tvDayAfterTwoDay_Week, tvDayAfterTwoDay_MaxTemperture, tvDayAfterTwoDay_MinTemperture;
    //之后第三天
    TextView tvDayAfterThreeDay, tvDayAfterThreeDay_Week, tvDayAfterThreeDay_MaxTemperture,
            tvDayAfterThreeDay_MinTemperture;
    //之后第四天
    TextView tvDayAfterFourDay, tvDayAfterFourDay_Week, tvDayAfterFourDay_MaxTemperture,
            tvDayAfterFourDay_MinTemperture;
    //之后第五天
    TextView tvDayAfterFiveDay, tvDayAfterFiveDay_Week, tvDayAfterFiveDay_MaxTemperture,
            tvDayAfterFiveDay_MinTemperture;
    //之后第六天
    TextView tvDayAfterSixDay, tvDayAfterSixDay_Week, tvDayAfterSixDay_MaxTemperture, tvDayAfterSixDay_MinTemperture;
    //之后第七天
    TextView tvDayAfterSevenDay, tvDayAfterSevenDay_Week, tvDayAfterSevenDay_MaxTemperture,
            tvDayAfterSevenDay_MinTemperture;
    //小时的信息
    TextView tvHourOneTime, tvHourOneTemperture, tvHourTwoTime, tvHourTwoTemperture, tvHourThreeTime, tvHourThreeTemperture;
    TextView tvHourFourTime, tvHourFourTemperture, tvHourFiveTime, tvHourFiveTemperture, tvHourSixTime, tvHourSixTemperture;
    TextView tvHourSevenTime, tvHourSevenTemperture, tvHourEightTime, tvHourEightTemperture;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//使标题栏消失
        setContentView(R.layout.activity_weather);
        System.out.println("WeatherActivity");
        findView();//让所有组件与界面建立联系
        intent =getIntent();
        String citymsg= intent.getStringExtra("data");//获得从搜索城市界面中输入的城市
        System.out.println(" citymsg"+citymsg);
        float dpDimension = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16,this.getResources().getDisplayMetrics());
        System.out.println(dpDimension+"hh");
//        float pxValue = getResources().getDimension(R.dimen.sp_15);//获取对应资源文件下的sp值
//        int spValue = DisplayUtil. ;px2sp(this, pxValue);//将px值转换成sp值
//        tvHourOneTime.setTextSize(spValue);//设置文字大小
//
//        /*获取dp值*/
//        float pxValue2 = getResources().getDimension(R.dimen.dp_360);//获取对应资源文件下的dp值
//        int dpValue = ConvertUtils.px2dp(this, pxValue2);//将px值转换成dp值

        if(citymsg ==null){//判断是否从MainMeunActivity跳转到此页面,如果为null，则是
            tvcity = updateWithNewLocation();//通过定位和数据获得城市
            System.out.println("SSSS"+tvcity);
            GPSUtils gpsUtils = GPSUtils.getInstance(WeatherActivity.this);
            if(tvcity == null){//GPS没有打开
                System.out.println("GPS没有打开");
                Toast.makeText(WeatherActivity.this,"请打开GPS",Toast.LENGTH_LONG).show();//和打开Intent的顺序相反
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);//跳转到定位设置界面
                startActivity(intent);
                finish();
            }else if(!gpsUtils.isNetProviderEnabled()){//有位置但没网
                System.out.println("GPS打开但网络没有打开");
                Toast.makeText(WeatherActivity.this,"请打开网络",Toast.LENGTH_LONG).show();
                intent = new Intent(android.provider.Settings.ACTION_DATA_ROAMING_SETTINGS);//跳转到流量设置界面
                startActivity(intent);
                finish();
            }else {
                intent =getIntent();
                Handler handler =new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String citymsg= intent.getStringExtra("data");
                        System.out.println(" citymsg"+citymsg);
                        if(citymsg == null){//判断是否从搜索框里获得的,为空则是从Main_menu跳转到此页面
                            tvcity = updateWithNewLocation();
                            if(tvcity == null){
                                System.out.println("tvcity"+tvcity);
                            }else{
                                System.out.println(tvcity+"  GPS获得的地址");
                            }
                        }else{
                            if(!citymsg.equals("")){//不为"",为""则获取当前的地点
                                tvcity = citymsg;
                            }else {
                                tvcity = updateWithNewLocation();
                                System.out.println(tvcity+"  GPS获得的地址");
                            }
                        }

                        Message msg = new Message();
                        Bundle data = new Bundle();
                        data.putString("value", "thread");
                        msg.setData(data);
                        mHandler.sendMessage(msg);
                    }
                },2000);
            }
        }else {
            if(!citymsg.equals("")){//不为"",为""则获取当前的地点
                tvcity = citymsg;
            }else {
                tvcity = updateWithNewLocation();
                System.out.println(tvcity+"  GPS获得的地址");
            }

            Message msg = new Message();
            Bundle data = new Bundle();
            data.putString("value", "thread");
            msg.setData(data);
            mHandler.sendMessage(msg);
        }

        fabAddCity.setOnClickListener(new View.OnClickListener() {//跳转到SearchCityActivity
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(WeatherActivity.this,SearchCityActivity.class);
                finish();
                startActivity(intent);
            }
        });
        reGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//实时更新数据

                Handler handler=new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            updata("rtweather",tvcity);
                            updata("weather.future",tvcity);
                            updata("weather.today",tvcity);
                            updata("weather.pm25",tvcity);

                        }catch (Exception e){
                            System.out.println("cw"+e);
                        }

                    }
                });

                System.out.println("reGetButton");
            }
        });
        nowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvcity = updateWithNewLocation();
                if(tvcity == null){//判断定位是否打开
                    Toast.makeText(WeatherActivity.this,"请打开GPS",Toast.LENGTH_LONG).show();//和打开Intent的顺序相反
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }else {
                    System.out.println("城市更新成功"+tvcity);
                    city.setText(tvcity);
                    Handler handler=new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                updata("rtweather",tvcity);
                                updata("weather.future",tvcity);
                                updata("weather.today",tvcity);
                                updata("weather.pm25",tvcity);

                            }catch (Exception e){
                                System.out.println("cw"+e);
                            }
                        }
                    });
                }
            }
        });

        hashMapView = new HashMap();//存数据
    }

    @Override
    protected void onResume() {
        super.onResume();
         thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread ");
            }
        });
        thread.start();


    }
    public String getTomorray(String stringtoday){//获得明天的日期
        Calendar calendar = new GregorianCalendar();//获得一个日期表
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date today = dateFormat.parse(stringtoday);
            calendar.setTime(today);
            calendar.add(calendar.DATE, 1);
        } catch (Exception e) {
            System.out.println(e);
        }
        String tomorrary = dateFormat.format(calendar.getTime());
        return tomorrary;
    }

    public void findView(){
        fabAddCity = findViewById(R.id.fabAddCity);
        nowLocation = findViewById(R.id.nowLocation);
        reGet = findViewById(R.id.reGet);
        city = findViewById(R.id.city);

        tvDayToday_Date = findViewById(R.id.tvDayToday_Date);
        ivDayToday_ImageView = findViewById(R.id.ivDayToday_ImageView);
        tvDayToday_NowTemperture = findViewById(R.id.tvDayToday_NowTemperture);
        tvDayToday_ScopeTemperture = findViewById(R.id.tvDayToday_ScopeTemperture);
        tvDayToday_WeatherChange = findViewById(R.id.tvDayToday_WeatherChange);
        tvDayToday_AirQualityNum = findViewById(R.id.tvDayToday_AirQualityNum);
        tvDayToday_AirQualityGrade = findViewById(R.id.tvDayToday_AirQualityGrade);

        tvDayAfterOneDay = findViewById(R.id.tvDayAfterOneDay);
        tvDayAfterOneDay_Week = findViewById(R.id.tvDayAfterOneDay_Week);
        ivDayAfterOneDay_ImageView = findViewById(R.id.ivDayAfterOneDay_ImageView);
        tvDayAfterOneDay_MaxTemperture = findViewById(R.id.tvDayAfterOneDay_MaxTemperture);
        tvDayAfterOneDay_MinTemperture = findViewById(R.id.tvDayAfterOneDay_MinTemperture);

        tvDayAfterTwoDay = findViewById(R.id.tvDayAfterTwoDay);
        tvDayAfterTwoDay_Week = findViewById(R.id.tvDayAfterTwoDay_Week);
        ivDayAfterTwoDay_ImageView = findViewById(R.id.ivDayAfterTwoDay_ImageView);
        tvDayAfterTwoDay_MaxTemperture = findViewById(R.id.tvDayAfterTwoDay_MaxTemperture);
        tvDayAfterTwoDay_MinTemperture = findViewById(R.id.tvDayAfterTwoDay_MinTemperture);


        tvDayAfterThreeDay = findViewById(R.id.tvDayAfterThreeDay);
        tvDayAfterThreeDay_Week = findViewById(R.id.tvDayAfterThreeDay_Week);
        ivDayAfterThreeDay_ImageView = findViewById(R.id.ivDayAfterThreeDay_ImageView);
        tvDayAfterThreeDay_MaxTemperture = findViewById(R.id.tvDayAfterThreeDay_MaxTemperture);
        tvDayAfterThreeDay_MinTemperture = findViewById(R.id.tvDayAfterThreeDay_MinTemperture);

        tvDayAfterFourDay = findViewById(R.id.tvDayAfterFourDay);
        tvDayAfterFourDay_Week = findViewById(R.id.tvDayAfterFourDay_Week);
        ivDayAfterFourDay_ImageView = findViewById(R.id.ivDayAfterFourDay_ImageView);
        tvDayAfterFourDay_MaxTemperture = findViewById(R.id.tvDayAfterFourDay_MaxTemperture);
        tvDayAfterFourDay_MinTemperture = findViewById(R.id.tvDayAfterFourDay_MinTemperture);

        tvDayAfterFiveDay = findViewById(R.id.tvDayAfterFiveDay);
        tvDayAfterFiveDay_Week = findViewById(R.id.tvDayAfterFiveDay_Week);
        ivDayAfterFiveDay_ImageView = findViewById(R.id.ivDayAfterFiveDay_ImageView);
        tvDayAfterFiveDay_MaxTemperture = findViewById(R.id.tvDayAfterFiveDay_MaxTemperture);
        tvDayAfterFiveDay_MinTemperture = findViewById(R.id.tvDayAfterFiveDay_MinTemperture);

        tvDayAfterSixDay = findViewById(R.id.tvDayAfterSixDay);
        tvDayAfterSixDay_Week = findViewById(R.id.tvDayAfterSixDay_Week);
        ivDayAfterSixDay_ImageView = findViewById(R.id.ivDayAfterSixDay_ImageView);
        tvDayAfterSixDay_MaxTemperture = findViewById(R.id.tvDayAfterSixDay_MaxTemperture);
        tvDayAfterSixDay_MinTemperture = findViewById(R.id.tvDayAfterSixDay_MinTemperture);

        tvDayAfterSevenDay = findViewById(R.id.tvDayAfterSevenDay);
        tvDayAfterSevenDay_Week = findViewById(R.id.tvDayAfterSevenDay_Week);
        ivDayAfterSevenDay_ImageView = findViewById(R.id.ivDayAfterSevenDay_ImageView);
        tvDayAfterSevenDay_MaxTemperture = findViewById(R.id.tvDayAfterSevenDay_MaxTemperture);
        tvDayAfterSevenDay_MinTemperture = findViewById(R.id.tvDayAfterSevenDay_MinTemperture);

        tvHourOneTime = findViewById(R.id.tvHourOneTime);
        tvHourOneTemperture = findViewById(R.id.tvHourOneTemperture);
        ivHourOneImage = findViewById(R.id.ivHourOneImage);

        tvHourTwoTime = findViewById(R.id.tvHourTwoTime);
        tvHourTwoTemperture = findViewById(R.id.tvHourTwoTemperture);
        ivHourTwoImage = findViewById(R.id.ivHourTwoImage);

        tvHourThreeTime = findViewById(R.id.tvHourThreeTime);
        tvHourThreeTemperture = findViewById(R.id.tvHourThreeTemperture);
        ivHourThreeImage = findViewById(R.id.ivHourThreeImage);

        tvHourFourTime = findViewById(R.id.tvHourFourTime);
        tvHourFourTemperture = findViewById(R.id.tvHourFourTemperture);
        ivHourFourImage = findViewById(R.id.ivHourFourImage);

        tvHourFiveTime = findViewById(R.id.tvHourFiveTime);
        tvHourFiveTemperture = findViewById(R.id.tvHourFiveTemperture);
        ivHourFiveImage = findViewById(R.id.ivHourFiveImage);

        tvHourSixTime = findViewById(R.id.tvHourSixTime);
        tvHourSixTemperture = findViewById(R.id.tvHourSixTemperture);
        ivHourSixImage = findViewById(R.id.ivHourSixImage);

        tvHourSevenTime = findViewById(R.id.tvHourSevenTime);
        tvHourSevenTemperture = findViewById(R.id.tvHourSevenTemperture);
        ivHourSevenImage = findViewById(R.id.ivHourSevenImage);

        tvHourEightTime = findViewById(R.id.tvHourEightTime);
        tvHourEightTemperture = findViewById(R.id.tvHourEightTemperture);
        ivHourEightImage = findViewById(R.id.ivHourEightImage);
    }
    /*
     *替换imageView图标
     */
    public  void displayiv(String weathercondition, ImageView view){//显示天气图标
        System.out.println("displayiv "+weathercondition);
        switch (weathercondition){
            case "阴":
                view.setImageResource(R.drawable.ying);
                break;
            case "晴":
                view.setImageResource(R.drawable.qing);
                break;
            case "多云":
                view.setImageResource(R.drawable.duoyun);
                break;
            case "小雨":
                view.setImageResource(R.drawable.xiaoyu);
                break;
            case "中雨":
                view.setImageResource(R.drawable.zhongyu);
                break;
            case "大雨":
                view.setImageResource(R.drawable.dayu);
                break;
            case "暴雨":
                view.setImageResource(R.drawable.baoyu);
                break;
            case "大暴雨":
                view.setImageResource(R.drawable.dabaoyu);
                break;
            case "特大暴雨":
                view.setImageResource(R.drawable.tedabaoyu);
                break;
            case "小雪":
                view.setImageResource(R.drawable.xiaoyue);
                break;
            case "中雪":
                view.setImageResource(R.drawable.zhongyue);
                break;
            case "大雪":
                view.setImageResource(R.drawable.dayue);
                break;
            case "暴雪":
                view.setImageResource(R.drawable.dabaoyue);
                break;
            default:
                view.setImageResource(R.drawable.qing);
        }
    }
    public static String addSpace(String temp){//给之后每天的最高温和最低温添加空格,使图标对齐
        String tempAddSpace = temp;
            switch (tempAddSpace.length()){//长度为4则不添加空格
                case 1:tempAddSpace = "   "+tempAddSpace;//添加三个空格
                break;
                case 2:tempAddSpace = "  "+tempAddSpace;
                break;
                case 3:tempAddSpace =" "+tempAddSpace;//添加两个空格
                    break;
                default:
                    break;
            }
        return tempAddSpace;
    }
    public static String dateToWeek(String datetime ){//日期转成星期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] weeks = {"周日", "周一","周二","周三","周四","周五","周六"};
        Calendar cl = Calendar.getInstance();
        Date datet = null;
        try{
            datet = simpleDateFormat.parse(datetime);
        }catch (ParseException e){
            System.out.println(e);
        }
        int w = cl.get(Calendar.DAY_OF_WEEK);//返回值1-7
        if (w < 0){
            w = 0;
        }
//        System.out.println(w);
        return weeks[w];
    }
    /*
     *通过GPS获得当前城市地址
     */
    private String  getWithNewLocation(Location location) {
        String latLongString = "北京";
        double lat = 32.6;
        double lng = 39.4;
        if (location != null) {
             lat = location.getLatitude();
             lng = location.getLongitude();
             System.out.println(" location2");
             System.out.println("  lat+lng"+lat+lng);
        } else {
            latLongString = "成都";

        }
        List<android.location.Address> addList = new ArrayList<>(50);
        Geocoder ge = new Geocoder(WeatherActivity.this);
        try {
            addList = ge.getFromLocation(lat, lng, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(addList!= null && addList.size()>0){
            for(int i=0; i<addList.size(); i++){
                Address ad = addList.get(i);
                latLongString = ad.getLocality();
                System.out.println("huodedechengshi "+latLongString);
            }
        }
        if(location != null){
            int i = latLongString.length();
            System.out.println("lat长度"+i);
            if(i >= 2){//将:city市的市截取掉
                latLongString = latLongString.substring(0,latLongString.length()-1);
            }
        }else {
            System.out.println("location为空");
        }
        return latLongString;
    }

    /*
     *点击地点图标获得所在城市地点
     * @return
     */
    private String updateWithNewLocation(){
        String locationcity = null;
        GPSUtils gpsUtils = GPSUtils.getInstance(WeatherActivity.this);
        if(gpsUtils.isLocationProviderEnabled()){//如果有GPS和网络才执行
            Location location = gpsUtils.getLocation();
            locationcity = getWithNewLocation(location);
        }
        return locationcity;
    }

    public void updata(final String app, final String cityid)//更新数据
    {

        final String appkey ="46951";
        final String sign = "b2f1992fc55dfd5ae70895f60ab3a86d";
        final String format ="json";

        if(app.equals("rtweather")){
            Runnable networkTask = new Runnable() {
                public static final String cn_to_unicode = "1";//中文转码
                public static final String token = "7f045595e3a7aac564a0eb746322d84f";//点睛密钥
                public static final String datatype = "json";
                @Override
                public void run() {
                    try {
                        URL url = new URL("http://api.djapi.cn/rtweather/get");

                        HttpURLConnection connect = (HttpURLConnection)url.openConnection();
                        connect.addRequestProperty("encoding","UTF-8");
                        connect.setDoInput(true);
                        connect.setDoOutput(true);
                        connect.setRequestMethod("GET");//POST or GET
                        OutputStream output = connect.getOutputStream();
                        OutputStreamWriter outputstreamreader = new OutputStreamWriter(output);
                        BufferedWriter writer = new BufferedWriter(outputstreamreader);
                        // 发送 请求
                        String params="&cityname_ch="+cityid
                                +"&cn_to_unicode="+cn_to_unicode+
                                "&token="+token+"&datatype="+datatype;
                        writer.write(params);
                        // 强制清空缓冲区 输出数据
                        writer.flush();

                        // 设置好 输入流 -- 因为 只有发送数据之后才会有接收数据
                        InputStream inputstream = connect.getInputStream();
                        InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
                        BufferedReader bufferreader = new BufferedReader(inputstreamreader);
                        // 上述步骤将数据封装好之后 即可将数据读取出来了
                        String outputstring;
                        StringBuilder strbuilder = new StringBuilder();
                        while((outputstring=bufferreader.readLine())!= null){
                            strbuilder.append(outputstring);
                        }
                        System.out.println(strbuilder);
                        try{
                            jsonObject_weather = new JSONObject(strbuilder.toString());
                            System.out.println("WEATHER f"+jsonObject_weather);
                            Iterator<String> iterable = jsonObject_weather.keys();
                            System.out.println();
                            while (iterable.hasNext()){
                                String skey = iterable.next();
                                System.out.println("Weather.java  "+skey);
                            }
                        }catch (JSONException e){
                            System.out.println(e);
                        }
                        writer.close();
                        bufferreader.close();
                        output.close();
                        outputstreamreader.close();
                        inputstream.close();
                        inputstreamreader.close();
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("value", "hour");
                    msg.setData(data);
                    mHandler.sendMessage(msg);
                }
            };
            new Thread(networkTask).start();
        }
        if(app.equals("weather.future"))//NowApi中的数据
        {
            Runnable networkTask = new Runnable() {
                @Override
                public void run() {
                    // TODO
                    // 在这里进行 http request.网络请求相关操作
                    try
                    {
                        URL u=new URL("http://api.k780.com/?app="+app+"&weaid="+cityid+"&appkey="+appkey+"&sign="+sign+"&format="+format);
                        InputStream in=u.openStream();
                        ByteArrayOutputStream out=new ByteArrayOutputStream();
                        try {
                            byte buf[]=new byte[1024];
                            int read = 0;
                            while ((read = in.read(buf)) > 0) {
                                out.write(buf, 0, read);
                            }
                        }  finally {
                            if (in != null) {
                                in.close();
                            }
                        }
                        byte b[]=out.toByteArray( );
                        jsonObject_weatherNowApi = new JsonParser().parse(out.toString()).getAsJsonObject();
                        System.out.println("hhh"+new String(b,"utf-8"));
                        System.out.println("WEATHER.JAVA +"+app +returnData.get("success").getAsString());

                    }catch (Exception e)
                    {
                        System.out.println("upcw:"+e);
                    }
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("value", "future");
                    msg.setData(data);
                    mHandler.sendMessage(msg);

                }
            };
            new Thread(networkTask).start();
        }
        else if(app.equals("weather.today"))
        {
            Runnable networkTask = new Runnable() {
                @Override
                public void run() {
                    // TODO
                    // 在这里进行 http request.网络请求相关操作
                    try
                    {
                        URL u=new URL("http://api.k780.com/?app="+app+"&weaid="+cityid+"&appkey="+appkey+"&sign="+sign+"&format="+format);
                        InputStream in=u.openStream();
                        ByteArrayOutputStream out=new ByteArrayOutputStream();
                        try {
                            byte buf[]=new byte[1024];
                            int read = 0;
                            while ((read = in.read(buf)) > 0) {
                                out.write(buf, 0, read);
                            }
                        }  finally {
                            if (in != null) {
                                in.close();
                            }
                        }
                        byte b[]=out.toByteArray( );
                        jsnObweatherNowApi_today = new JsonParser().parse(out.toString()).getAsJsonObject();
                        returnData=jsnObweatherNowApi_today;
                        System.out.println("hhh"+new String(b,"utf-8"));
                        System.out.println("WEATHER.JAVA +"+app +returnData.get("success").getAsString());

                    }catch (Exception e)
                    {
                        System.out.println("upcw:"+e);
                    }
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("value", "today");
                    msg.setData(data);
                    mHandler.sendMessage(msg);
                }
            };
            new Thread(networkTask).start();
        }
        else if(app.equals("weather.pm25"))
        {
            Runnable networkTask = new Runnable() {
                @Override
                public void run() {
                    // TODO
                    // 在这里进行 http request.网络请求相关操作
                    try
                    {
                        URL u=new URL("http://api.k780.com/?app="+app+"&weaid="+cityid+"&appkey="+appkey+"&sign="+sign+"&format="+format);
                        InputStream in=u.openStream();
                        ByteArrayOutputStream out=new ByteArrayOutputStream();
                        try {
                            byte buf[]=new byte[1024];
                            int read = 0;
                            while ((read = in.read(buf)) > 0) {
                                out.write(buf, 0, read);
                            }
                        }  finally {
                            if (in != null) {
                                in.close();
                            }
                        }
                        byte b[]=out.toByteArray( );
                        jsnObweatherNowApi_today_airquality = new JsonParser().parse(out.toString()).getAsJsonObject();
                        returnData=jsnObweatherNowApi_today_airquality;
                        System.out.println("hhh"+new String(b,"utf-8"));
                        System.out.println("WEATHER.JAVA +"+app +returnData.get("success").getAsString());

                    }catch (Exception e)
                    {
                        System.out.println("upcw:"+e);
                    }
                    Message msg = new Message();
                    Bundle data = new Bundle();
                    data.putString("value", "pm25");
                    msg.setData(data);
                    mHandler.sendMessage(msg);
                }
            };
            new Thread(networkTask).start();
        }
    }
}
