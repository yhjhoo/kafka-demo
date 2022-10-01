package me.prince.kafkademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@SpringBootApplication
@EnableKafka
public class KafkaDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaDemoApplication.class, args);
    }

}

@Service
class Listener {
    @KafkaListener(topics = "quickstart-events1", groupId = "default")
    void listen(String data) {
        System.out.println(data);
    }
}
