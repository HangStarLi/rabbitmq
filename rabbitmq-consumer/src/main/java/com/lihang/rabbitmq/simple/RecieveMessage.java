package com.lihang.rabbitmq.simple;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class RecieveMessage {
    private static final String QUEUE_NAME = "test-simple-queue";
    public static void main(String[] args) throws IOException, InterruptedException {
        /*新方法*/
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
         //声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"utf-8");
                System.out.println(message);
            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
    /*
    * 旧的监听消息的方法
    * 不建议使用
    * */
    @Test
    public void oldRecieveMessage() throws IOException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtil.getConnection();
        //获取通道
        Channel channel = connection.createChannel();
        //定义队列消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
        while(true){
            QueueingConsumer.Delivery delivery =  consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(message);
        }
    }
}
