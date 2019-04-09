package com.kd.receiver;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.kd.annotation.CheckNet;
import com.kd.bean.MethodManager;
import com.kd.util.NetType;
import com.kd.util.NetWorkManager;
import com.kd.util.NetWorkUtil;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NetChangeReceiver extends BroadcastReceiver {
    private static final String action = "android.net.conn.CONNECTIVITY_CHANGE";
    private Map<Object,List<MethodManager>> cacheMap;
    public NetChangeReceiver(){
        cacheMap = new HashMap<>();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        if(null==intent||TextUtils.isEmpty(intent.getAction())){
            return;
        }
        if(action.equals(intent.getAction())){
           NetType netType =  NetWorkUtil.getNetType();
            dispense(netType);
        }
    }
    /**
     * 网络变化通知所有注册者
     */
    private void dispense(NetType netType) {
        if(cacheMap.isEmpty()){
            return;
        }
        Set<Object> keys = cacheMap.keySet();
        for(Object key:keys){
            List<MethodManager> list =  cacheMap.get(key);
            if(list!=null&&!list.isEmpty()){
                for(MethodManager methodManager:list){
                    Method method = methodManager.getMethod();
                    //检查参数类型
                    if(method.getParameterTypes()[0]==NetType.class){
                        switch (netType){
                            case ALL:
                                invoke(method,key,netType);
                                break;
                            case WIFI:
                                invoke(method,key,netType);
                                break;
                            case GPRS:
                                invoke(method,key,netType);
                                break;
                            case NONE:
                                invoke(method,key,netType);
                                break;
                        }
                    }
                }
            }
        }
    }

    private void invoke(Method method, Object key, NetType netType) {
        if(method==null||key==null||netType==null){
            return;
        }
        try {
            method.invoke(key,netType);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void registerObserver(Object object) {
        List<MethodManager> list = cacheMap.get(object);
        if(null==list||list.isEmpty()){
            //开始添加
            list = findAnnotationMethod(object);
            cacheMap.put(object,list);
        }
    }

    private List<MethodManager> findAnnotationMethod(Object object) {
        List<MethodManager> managerList = new ArrayList<>();
        if(null==object){
            return null;
        }
        Class<?> clazz = object.getClass();
        if(null!=clazz){
            Method[] methods = clazz.getDeclaredMethods();
            if(null!=methods&&methods.length>0){
                for(Method method:methods){
                    CheckNet checkNet =  method.getAnnotation(CheckNet.class);
                    if(null!=checkNet){
                        Type type =  method.getGenericReturnType();
                        Class<?>[] classes = method.getParameterTypes();
                        //根据方法返回值和形参个数
                        if("void".equalsIgnoreCase(type.toString())&&classes.length==1){
                                MethodManager methodManager = new MethodManager(classes[0],checkNet.netType(),method);
                            managerList.add(methodManager);
                        }
                    }
                }
            }
        }
        return managerList;
    }

    public void unRegisterObserver(Object object) {
        if(!cacheMap.isEmpty()&&object!=null){
            cacheMap.remove(object);
        }
    }

    public void unRegisterAllObserver() {
        if(!cacheMap.isEmpty()){
            cacheMap.clear();
        }
        NetWorkManager.getInstance().unRegisterReceiver(this);
        cacheMap = null;
    }
}
