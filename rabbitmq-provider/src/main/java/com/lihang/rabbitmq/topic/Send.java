package com.lihang.rabbitmq.topic;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    /*
    * Topic主题模式
    * */
    private static final String EXCHANGE_NAME = "test-exchange-topic";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //声明exchange为topic主题模式
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        //发送消息
        String msg ="hello topic ";
        //设置路由
        String routingKey = "goods.add";
        channel.basicPublish(EXCHANGE_NAME,routingKey,null,msg.getBytes());
        channel.close();
        connection.close();
    }
}
