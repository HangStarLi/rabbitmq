package com.lihang.rabbitmq.utils;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {
    /*
    *获取链接
    */
    public static Connection getConnection(){
        //创建链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置host
        factory.setHost("127.0.0.1");
        //设置AMQP端口号
        factory.setPort(5672);
        //设置数据库
        factory.setVirtualHost("/vhost_mmr");
        //设置用户名密码
        factory.setUsername("lihang");
        factory.setPassword("1234");

        try {
            return factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
}
