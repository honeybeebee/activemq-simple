/**
 * @Title: Producer.java
 * @Description: 生产者
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @Date: 2019-04-16 17:20
 * <p>
 * 修改历史:
 * Date                 Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2019-04-16 17:20     xlfxu        1.0            初始化版本
 */
package com.xulinfeng.demo.activemq.simple.producer;

import com.xulinfeng.demo.activemq.simple.util.Constant;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 生产者
 *
 * @author xlfxu
 */
public class Producer {

    /**
     * 连接工厂
     */
    ConnectionFactory connectionFactory;

    /**
     * 连接对象
     */
    Connection connection;

    /**
     * 会话，由Connection创建，实质上就是发送、接受消息的一个线程
     */
    Session session;

    AtomicInteger count = new AtomicInteger(0);

    ThreadLocal<MessageProducer> threadLocal = new ThreadLocal<>();

    public void init() {
        try {
            //创建一个链接工厂
            connectionFactory = new ActiveMQConnectionFactory(Constant.USERNAME, Constant.PASSWORD, Constant.BROKEN_URL);
            //从工厂中创建一个链接
            connection = connectionFactory.createConnection();
            //开启链接
            connection.start();
            //创建一个会话（这里通过参数可以设置事务的级别）
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生产消息
     * @param destinationName
     */
    public void sendMessage(String destinationName) {
        try {
            //创建消息目标，就是消息发送和接受的地点，要么queue，要么topic
            Destination destination = session.createQueue(destinationName);
            //消息生产者
            MessageProducer messageProducer = null;
            if (threadLocal.get() != null) {
                messageProducer = threadLocal.get();
            } else {
                messageProducer = session.createProducer(destination);
                //设置持久化方式，非持久-MQ重启后消息将丢失；持久化-方式有kahdb、leveldb、jdbc
                messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
                threadLocal.set(messageProducer);
            }
            while (true) {
                Thread.sleep(1000);

                //创建一条消息
                String textMsg = Thread.currentThread().getName() + "productor：正在生产消息，count:" + count.getAndIncrement();
                TextMessage msg = session.createTextMessage(textMsg);
                System.out.println(textMsg);

                //发送消息
                messageProducer.send(msg);

                //提交事务
                session.commit();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
