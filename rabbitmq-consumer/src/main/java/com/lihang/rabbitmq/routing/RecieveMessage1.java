package com.lihang.rabbitmq.routing;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class RecieveMessage1 {
    /*
    * 路由模式
    * */
    private static final String EXCHANGE_NAME = "test-exchange-direct";
    private static final String QUEUE_NAME = "test-queue-direct1";
    public static void main(String[] args) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        //定义queue
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定exchange
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"error");
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("recieve[1] : "+msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("recieve[1] done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        //关闭自动应答
        Boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
}
