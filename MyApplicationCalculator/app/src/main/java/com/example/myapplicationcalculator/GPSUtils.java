package com.example.myapplicationcalculator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Address;


public class GPSUtils {
    private static String TAG = GPSUtils.class.getSimpleName();
    private static GPSUtils mInstance;
    private Context mContext;
    private static LocationListener mLocationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d(TAG, "onStatusChanged");
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
            Log.d(TAG, "onProviderEnabled");

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
            Log.d(TAG, "onProviderDisabled");

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
        }
    };


    private GPSUtils(Context context) {
        this.mContext = context;
    }

    public static GPSUtils getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new GPSUtils(context.getApplicationContext());
        }
        return mInstance;
    }

    /**
     * 获取地理位置，先根据GPS获取，再根据网络获取
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public Location getLocation() {
        Location location = null;
        try {
            if (mContext == null) {
                return null;
            }
            LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager == null) {
                System.out.println("GPSGEtMana"+locationManager);
                return null;
            }

            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {  //从gps获取经纬度
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null) {//当GPS信号弱没获取到位置的时候再从网络获取
                    System.out.println("GPSGetlocation"+" ");
                    location = getLocationByNetwork();
                }
                /*
                NETWORK_PROVIDER：
                这个就是利用网络定位，通常是利用手机基站和WIFI节点的地址来大致定位位置，
                这种定位方式取决于服务器，即取决于将基站或WIF节点信息翻译成位置信息的服务器的能力。
                由于目前大部分Android手机没有安装google官方的location manager库，大陆网络也不允许，
                即没有服务器来做这个事情，自然该方法基本上没法实现定位。
                 */
            }

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return location;
    }


    /**
     * 判断是否开启了GPS和网络定位开关
     *
     * @return
     */
    public boolean isLocationProviderEnabled() {//判断网络和定位
        boolean result = false;
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return result;
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            result = true;
        }
        return result;
    }

    /*
     *@return
     */
    public boolean isGPSProviderEnabled() {//判断GPS
        boolean result = false;
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            return result;
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            result = true;
        }
        return result;
    }

    /*
     *@return
     */
    public boolean isNetProviderEnabled() {//判断网络
        boolean result = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (connectivityManager == null) {
            return result;
        }
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            result = true;
        }
        return result;
    }

    /**
     * 获取地理位置，先根据GPS获取，再根据网络获取
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    private Location getLocationByNetwork() {
        Location location = null;
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        try {
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 5000, mLocationListener);
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                System.out.println("GPSUtil"+location.getLatitude()+" "+location.getLongitude());
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return location;
    }
}

