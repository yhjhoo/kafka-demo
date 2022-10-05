package me.prince.kafkademo;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@SpringBootApplication
@EnableKafka
public class KafkaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaDemoApplication.class, args);
    }

    @Bean
    public NewTopic topic() {
        return TopicBuilder.name("quickstart-events1")
                .partitions(2)
                .replicas(3)
                .compact()
                .build();
    }
}

@Service
class Listener {
    @KafkaListener(topics = "quickstart-events1", groupId = "default")
    void listen(String data) {
        System.out.println(data);
    }
}
