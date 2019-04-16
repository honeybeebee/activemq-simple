/**
 * @Title: TestProducer.java
 * @Description: 测试生产消息
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @Date: 2019-04-16 21:35
 * <p>
 * 修改历史:
 * Date                 Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2019-04-16 21:35     xlfxu        1.0            初始化版本
 */
package com.xulinfeng.demo.activemq.simple.test.producer;

import com.xulinfeng.demo.activemq.simple.producer.Producer;

/**
 * 测试生产消息
 *
 * @author xlfxu
 */
public class TestProducer {

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.init();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Thread 1
        new Thread(new ProducerThread(producer)).start();
        //Thread 2
        new Thread(new ProducerThread(producer)).start();
        //Thread 3
        new Thread(new ProducerThread(producer)).start();
    }

    public static class ProducerThread implements Runnable {
        Producer producer;

        public ProducerThread(Producer producer) {
            this.producer = producer;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    producer.sendMessage("demo-MQ");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
