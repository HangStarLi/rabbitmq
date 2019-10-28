package com.lihang.rabbitmq.routing;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    /*
    * 路由模式
    * */
    private static final String EXCHANGE_NAME = "test-exchange-direct";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //设置交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"direct");
        String msg = "hello exchange direct";
        //设置路由
        String routingKey = "info";
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
        System.out.println("send successfully");
        channel.close();
        connection.close();
    }
}
