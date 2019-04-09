package com.kd.bean;
import com.kd.util.NetType;
import java.lang.reflect.Method;

public class MethodManager {
    //参数类型
    private Class<?>  parameterType;
    //网络类型
    private NetType netType;
    //执行的方法
    private Method method;

    public MethodManager(Class<?> parameterType, NetType netType, Method method) {
        this.parameterType = parameterType;
        this.netType = netType;
        this.method = method;
    }

    public Class<?> getParameterType() {
        return parameterType;
    }

    public void setParameterType(Class<?> parameterType) {
        this.parameterType = parameterType;
    }

    public NetType getNetType() {
        return netType;
    }

    public void setNetType(NetType netType) {
        this.netType = netType;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
