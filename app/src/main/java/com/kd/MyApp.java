package com.kd;
import android.app.Application;
import com.kd.util.NetWorkManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetWorkManager.getInstance().init(this);
    }
}
