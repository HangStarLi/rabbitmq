package com.lihang.rabbitmq.workqueue;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class RecieveMessage1 {
    private static final String QUEUE_NAME = "test-work-queue";
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"utf-8");
                System.out.println("recieve-1: "+message);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("recieve-1 has finished");
                }
            }
        };
        boolean ACK = true;
        channel.basicConsume(QUEUE_NAME,ACK,consumer);
    }
}
