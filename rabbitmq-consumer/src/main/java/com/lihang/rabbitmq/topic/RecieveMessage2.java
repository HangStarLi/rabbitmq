package com.lihang.rabbitmq.topic;


import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class RecieveMessage2 {
    /*接收者2：接受goods所有消息*/
    private static final String EXCHANGE_NAME = "test-exchange-topic";
    private static final String QUEUE_NAME = "test-queue-topic2";
    public static void main(String[] ages) throws IOException {
        Connection connection = ConnectionUtil.getConnection();
        final Channel channel = connection.createChannel();
        //声明queue
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //绑定exchange,匹配所有消息
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"goods.#");
        channel.basicQos(1);
        //发送消息
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("recieve[2]: "+msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println("recieve[2] done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        channel.basicConsume(QUEUE_NAME,false,consumer);
    }
}
