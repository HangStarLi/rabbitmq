package com.lihang.rabbitmq.workqueue;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    /*
    * 这是轮询处理消息模式：无论消费者处理消息的快慢，消费者处理的消息都是一样多的
    * */
    private static final String QUEUE_NAME = "test-work-queue";
    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        for (int i = 0; i <100 ; i++) {
            String message = "hello work queue:"+i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            Thread.sleep(50*i);
        }
        channel.close();
        connection.close();
    }
}
