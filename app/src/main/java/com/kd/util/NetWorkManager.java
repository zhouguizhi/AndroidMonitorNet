package com.kd.util;
import android.app.Application;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import com.kd.receiver.NetChangeReceiver;

public class NetWorkManager {
    private Application application;
    private NetChangeReceiver receiver;
    private NetWorkManager(){
        receiver = new NetChangeReceiver();
    }

    public void registerObserver(Object object) {
        if(null==receiver){
            return;
        }
        receiver.registerObserver(object);
    }

    public void unRegisterObserver(Object object) {
        if(null==receiver){
            return;
        }
        receiver.unRegisterObserver(object);
    }
    public void unRegisterAllObserver() {
        if(null==receiver){
            return;
        }
        receiver.unRegisterAllObserver();
    }

    /**
     * 注销广播
     * @param netChangeReceiver
     */
    public void unRegisterReceiver(NetChangeReceiver netChangeReceiver) {
        if(null==application){
            return;
        }
        application.unregisterReceiver(receiver);
    }

    private static final class Holder{
        private static final NetWorkManager instance = new NetWorkManager();
    }
    public static NetWorkManager getInstance(){
        return Holder.instance;
    }
    public Application getApplication() {
        if(null==application){
            throw new RuntimeException("必须在Application中初始化");
        }
        return application;
    }
    public void init(Application application){
        this.application = application;
        registerReceiver();

    }

    private void registerReceiver() {
        if(null==application){
            return;
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        application.registerReceiver(receiver, filter);
    }
}
