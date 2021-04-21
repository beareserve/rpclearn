package cn.by.provider;

import cn.by.framework.Protocol;
import cn.by.framework.ProtocolFactory;
import cn.by.framework.URL;
import cn.by.provider.api.HelloService;
import cn.by.provider.impl.HelloServiceImpl;
import cn.by.register.RemoteMapRegister;

public class Provider {

    private static boolean isRun = true;

    public static void main(String[] args) {
        // 1. 注册服务
        // 2. 本地注册
        // 3. 启动tomcat

        // 注册服务
        URL url = new URL("localhost", 880); //NetUtil
        RemoteMapRegister.regist(HelloService.class.getName(), url);

        //  服务：实现类
        LocalRegister.regist(HelloService.class.getName(), HelloServiceImpl.class);


        Protocol protocol = ProtocolFactory.getProtocol();
        protocol.start(url);


    }
}
