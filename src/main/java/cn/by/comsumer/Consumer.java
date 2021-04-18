package cn.by.comsumer;


import cn.by.framework.ProxyFactory;
import cn.by.provider.api.HelloService;

public class Consumer {

    public static void main(String[] args) {

        HelloService helloService = ProxyFactory.getProxy(HelloService.class);
        String xxx = helloService.sayHello("xxx");
        System.out.println(xxx);



    }
}
