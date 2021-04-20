package cn.by.framework;

import cn.by.register.RemoteMapRegister;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class ProxyFactory {

    public static <T> T getProxy(final Class interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (StringUtils.equals("toString", method.getName())) {
                    System.out.println("偷偷进了代理？" + "对象：" + JSON.toJSONString(proxy) + "; 方法：" + method.getName() + ";参数：" + JSON.toJSONString(args));
                    return null;
                }
                String mock = System.getProperty("mock");
                if (mock != null && mock.startsWith("return:")) {
                    String result = mock.replace("return:", "");
                    return result;
                }

                Invocation invocation = new Invocation(interfaceClass.getName(), method.getName(), args, method.getParameterTypes());
                List<URL> urlList = RemoteMapRegister.get(interfaceClass.getName());
                URL url = LoadBalance.random(urlList);
                Protocol protocol = ProtocolFactory.getProtocol();
                String result = protocol.send(url, invocation);
                return result;
            }
        });
    }

}
