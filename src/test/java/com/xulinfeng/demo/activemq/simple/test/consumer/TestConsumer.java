/**
 * @Title: java
 * @Description: 测试消费消息
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @Date: 2019-04-16 21:36
 * <p>
 * 修改历史:
 * Date                 Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2019-04-16 21:36     xlfxu        1.0            初始化版本
 */
package com.xulinfeng.demo.activemq.simple.test.consumer;

import com.xulinfeng.demo.activemq.simple.consumer.Consumer;

/**
 * 测试消费消息
 *
 * @author xlfxu
 */
public class TestConsumer {
    
    public static void main(String[] args){
        Consumer consumer = new Consumer();
        consumer.init();
        
        new Thread(new ConsumerMq(consumer)).start();
        new Thread(new ConsumerMq(consumer)).start();
        new Thread(new ConsumerMq(consumer)).start();
        new Thread(new ConsumerMq(consumer)).start();
    }

    private static class ConsumerMq implements Runnable{
        Consumer consumer;

        public ConsumerMq(Consumer consumer){
            this.consumer = consumer;
        }

        @Override
        public void run() {
            while(true){
                try {
                    consumer.getMessage("demo-MQ");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
