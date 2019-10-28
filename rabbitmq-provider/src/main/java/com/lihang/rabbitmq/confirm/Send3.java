package com.lihang.rabbitmq.confirm;

import com.lihang.rabbitmq.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class Send3 {
    /*异步方式*/
    private static final String QUEUE_NAME = "test-queue-confirms";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        channel.confirmSelect();
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
        channel.addConfirmListener(new ConfirmListener() {
            //发送成功的
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple){//多条回值
                    confirmSet.headSet(deliveryTag+1).clear();
                }else{
                    confirmSet.remove(deliveryTag);
                }
            }
            //发送失败的
            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple){//多条回值
                    confirmSet.headSet(deliveryTag+1).clear();
                }else{
                    confirmSet.remove(deliveryTag);
                }
            }
        });
        String msg = "hello confirm";
        for (int i = 0; i < 50; i++) {
            Long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAME,null,(msg+i).getBytes());
            confirmSet.add(seqNo);
        }
        channel.close();
        connection.close();
    }
}
