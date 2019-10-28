package com.lihang.rabbitmq.simple;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


public class SendMessage {
    private static final String QUEUE_NAME = "test-simple-queue";
    public static void main(String[] args) throws IOException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //从连接中获取通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //创建发送消息
        String message = "hello simple queue";
        channel.basicPublish("",QUEUE_NAME,null,message.getBytes());

        //关闭资源
        try {
            channel.close();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        connection.close();
    }
}
