package com.lihang.rabbitmq.tx;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxSend {
    /*
    * 事务：确保发送到rabbitMQ
    * */
    private static final String QUEUE_NAME = "test-queue-tx";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg = "hello tx";
        try {
            //开启事务
            channel.txSelect();
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            channel.txCommit();
        }catch (Exception e){
            e.printStackTrace();
            channel.txRollback();
        }finally {
            channel.close();
            connection.close();
        }
    }
}
