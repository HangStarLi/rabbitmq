package com.lihang.rabbitmq.workqueuefair;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class RecieveMessageFair2 {
    private static final String QUEUE_NAME = "test-work-queue";
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //每次只分发一条消息
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"utf-8");
                System.out.println("recieve-2: "+message);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("recieve-2 has finished");
                    //手动应答，消息处理完了，可以处理下一条信息了
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //关闭自动应答
        boolean ACK = false;
        channel.basicConsume(QUEUE_NAME,ACK,consumer);
    }
}
