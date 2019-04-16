/**
 * @Title: Consumer.java
 * @Description: 消费者
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @Date: 2019-04-16 21:26
 * <p>
 * 修改历史:
 * Date                 Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2019-04-16 21:26     xlfxu        1.0            初始化版本
 */
package com.xulinfeng.demo.activemq.simple.consumer;

import com.xulinfeng.demo.activemq.simple.util.Constant;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 消费者
 *
 * @author xlfxu
 */
public class Consumer {

    ConnectionFactory connectionFactory;

    Connection connection;

    Session session;

    AtomicInteger count = new AtomicInteger();

    ThreadLocal<MessageConsumer> threadLocal = new ThreadLocal<>();


    public void init() {
        try {
            connectionFactory = new ActiveMQConnectionFactory(Constant.USERNAME, Constant.PASSWORD, Constant.BROKEN_URL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


    /**
     * 消费消息
     * @param destinationName
     */
    public void getMessage(String destinationName) {
        try {
            Destination destination = session.createQueue(destinationName);
            //消息消费者
            MessageConsumer consumer = null;

            if (threadLocal.get() != null) {
                consumer = threadLocal.get();
            } else {
                consumer = session.createConsumer(destination);
                threadLocal.set(consumer);
            }
            while (true) {
                Thread.sleep(1000);

                //接收消息
                TextMessage textMessage = (TextMessage) consumer.receive();

                if (textMessage != null) {
                    textMessage.acknowledge();
                    System.out.println(Thread.currentThread().getName() + ": Consumer:正在消费消息。消息内容：" + textMessage.getText() + "，count" + count.getAndIncrement());
                } else {
                    break;
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
