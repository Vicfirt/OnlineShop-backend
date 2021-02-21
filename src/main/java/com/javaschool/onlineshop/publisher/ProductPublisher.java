package com.javaschool.onlineshop.publisher;

import com.javaschool.onlineshop.model.entity.Product;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advertisement")
public class ProductPublisher {

    private final RabbitTemplate template;

    @Value("${spring.rabbitmq.template.exchange}")
    private String topicExchangeName;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey;

    public ProductPublisher(RabbitTemplate template) {
        this.template = template;
    }

    @GetMapping
    public String getProducts(){
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("Some");
        template.convertAndSend(topicExchangeName, routingKey, product);
        return "Message has been send";
    }
}
