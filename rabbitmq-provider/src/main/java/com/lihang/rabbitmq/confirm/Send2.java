package com.lihang.rabbitmq.confirm;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send2 {
    /*普通模式，只发一批*/
    private static final String QUEUE_NAME = "test-queue-confirm";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        String msg = "hello confirm";
        try {
            //开启confirm
            channel.confirmSelect();
            for (int i = 0; i < 10; i++) {
                channel.basicPublish("",QUEUE_NAME,null,(msg+i).getBytes());
            }
            if(!channel.waitForConfirms()){
                System.out.println("send failed");
            }else{
                System.out.println("send successed");
            }
        }catch (Exception e){
            e.printStackTrace();
            channel.txRollback();
        }finally {
            channel.close();
            connection.close();
        }
    }
}
