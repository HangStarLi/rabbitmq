package com.lihang.rabbitmq.workqueuefair;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SendFair {
    /*
    * 这是公平处理消息模式：谁处理的快，谁就处理的多
    * */
    private static final String QUEUE_NAME = "test-work-queue";
    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //保证一次只分发一个消息
        channel.basicQos(1);

        for (int i = 0; i <100 ; i++) {
            String message = "hello work queue:"+i;
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
           // Thread.sleep(50*i);
        }
        channel.close();
        connection.close();
    }
}
