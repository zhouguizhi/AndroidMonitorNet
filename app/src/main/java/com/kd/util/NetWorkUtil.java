package com.kd.util;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtil {
    /**
     * 判断网络是否可用
     * @return false网络不可用  true网络可用
     */
    public static boolean isNetworkConnected() {
        if (NetWorkManager.getInstance().getApplication() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) NetWorkManager.getInstance().getApplication()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    public static NetType getNetType(){
        if(isNetworkConnected()&&null!=NetWorkManager.getInstance().getApplication()){
            ConnectivityManager connectivityManager = (ConnectivityManager) NetWorkManager.getInstance().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if(null!=networkInfo){
                String type = networkInfo.getTypeName();
                if ("WIFI".equalsIgnoreCase(type)) {
                    return NetType.WIFI;
                } else if ("MOBILE".equalsIgnoreCase(type)) {
                    return NetType.GPRS;
                }
            }
        }
        return NetType.NONE;
    }
}
