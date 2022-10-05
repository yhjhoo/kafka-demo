package me.prince.kafkademo;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;


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
    @KafkaListener(topics = "quickstart-events1", groupId = "default", clientIdPrefix = "c1")
    void listen(
            @Payload String data,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
            @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets
    ) {
        System.out.println("");
        System.out.println("=================");


        System.out.format("partition: %s, topic: %s, offset: %s \n",
                partitions,
                topics,
                offsets
        );
        System.out.println("data:" + data);
    }
}
