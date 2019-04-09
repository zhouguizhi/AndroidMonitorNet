package com.kd;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.kd.annotation.CheckNet;
import com.kd.util.NetType;
import com.kd.util.NetWorkManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NetWorkManager.getInstance().registerObserver(this);
    }
    @CheckNet(netType = NetType.ALL)
    public void checkNetWork(NetType netType){
        Log.e("MainActivity","网络类型是--"+netType.name());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetWorkManager.getInstance().unRegisterObserver(this);
    }
}
