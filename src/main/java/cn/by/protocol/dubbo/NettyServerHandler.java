package cn.by.protocol.dubbo;

import cn.by.framework.Invocation;
import cn.by.provider.LocalRegister;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Invocation invocation = (Invocation) msg;

        Class serviceImpl = LocalRegister.get(invocation.getInterfaceName());

        Method method = serviceImpl.getMethod(invocation.getMethodName(), invocation.getParamType());
        Object result = method.invoke(serviceImpl.newInstance(), invocation.getParams());

        System.out.println("debug的invocation:" + JSON.toJSONString(invocation) + ";result--" + JSON.toJSONString(result));
        System.out.println("Netty服务端执行程序得到结果----" + result.toString() + "；开始返回客户端");
        ctx.writeAndFlush("Netty:" + result);
    }
}
