package com.scaler.bookmyshow.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaProducerConfig {
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaProducerConfig(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishEvent(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

}
