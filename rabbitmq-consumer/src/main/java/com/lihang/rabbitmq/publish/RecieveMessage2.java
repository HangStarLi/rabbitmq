package com.lihang.rabbitmq.publish;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RecieveMessage2 {
    private static final String QUEUE_NAME = "test-publish-queue-sms";
    private static final String EXCHANGE_NAME = "test-exchange-fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定到交换机
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");
        channel.basicQos(1);//每次只发一个
        //定义一个消费者
        Consumer consumer = new DefaultConsumer(channel){
            //消息到达时，触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8") ;
                System.out.println("consumer2:"+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("consumer[2] has done");
                    //手动应答
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        boolean autoACK = false;//关闭自动应答
        channel.basicConsume(QUEUE_NAME,autoACK,consumer);
    }
}
