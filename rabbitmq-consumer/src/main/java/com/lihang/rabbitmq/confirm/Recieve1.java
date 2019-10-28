package com.lihang.rabbitmq.confirm;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Recieve1 {
    /*普通模式，只发送一条*/
    private static final String QUEUE_NAME = "test-queue-confirm";
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.basicConsume(QUEUE_NAME,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("confirm recieve: "+new String(body,"utf-8"));
            }
        });
    }
}
