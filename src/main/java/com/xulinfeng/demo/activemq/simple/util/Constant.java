/**
 * @Title: Constant.java
 * @Description: 常量
 * <p>
 * Copyright(C) 2018-2019    NPlus
 * Company:   无锡核心信息科技有限公司
 * @version V1.0
 * @Date: 2019-04-16 21:01
 * <p>
 * 修改历史:
 * Date                 Author        Version        Discription
 * -----------------------------------------------------------------------------------
 * 2019-04-16 21:01     xlfxu        1.0            初始化版本
 */
package com.xulinfeng.demo.activemq.simple.util;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 常量
 *
 * @author xlfxu
 */
public class Constant {
    /**
     * ActiveMq 用户名
     */
    public static final String USERNAME = ActiveMQConnectionFactory.DEFAULT_USER;

    /**
     * ActiveMq 登录密码
     */
    public static final String PASSWORD = ActiveMQConnectionFactory.DEFAULT_PASSWORD;

    /**
     * ActiveMQ 链接地址
     */
    public static final String BROKEN_URL = ActiveMQConnectionFactory.DEFAULT_BROKER_URL;
}
