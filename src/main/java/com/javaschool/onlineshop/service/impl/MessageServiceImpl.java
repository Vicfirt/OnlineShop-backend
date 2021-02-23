package com.javaschool.onlineshop.service.impl;

import com.javaschool.onlineshop.service.MessageService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class MessageServiceImpl implements MessageService {

    @Value("${spring.rabbitmq.template.default-receive-queue}")
    private String queueName;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Override
    public void sendMessage(String message) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, false, null);
        channel.basicPublish("", queueName, null, message.getBytes("UTF-8"));
        LOGGER.info(" Message has been sent to the queue'" + message + "'");
        channel.close();
        connection.close();
    }
}
