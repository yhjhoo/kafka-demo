package me.prince.kafkademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
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

   /* @Bean
    public NewTopic topic() {
        return TopicBuilder.name("quickstart-events3")
                .partitions(20)
                .replicas(5)
                .compact()
                .build();
    }*/

    /*@Bean
    CommandLineRunner commandLineRunner(KafkaAdmin kafkaAdmin) {
        return args -> {
            System.out.println("111");
            AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties());
            DeleteTopicsResult result = adminClient.deleteTopics(Arrays.asList("quickstart-events6"));
            System.out.println(result);
            adminClient.close();
        };
    }*/
}

/*@Configuration
class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic("quickstart-events6", 1, (short) 1);
    }
}*/

@Service
class Listener {
    @KafkaListener(topics = "quickstart-events1", groupId = "default", clientIdPrefix = "c1")
    void listen(
            @Payload String data,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
            @Header(KafkaHeaders.RECEIVED_TOPIC) List<String> topics,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets
    ) {
        System.out.println("=================");


        System.out.format("partition: %s, topic: %s, offset: %s \n",
                partitions,
                topics,
                offsets
        );
        System.out.println("data:" + data);
    }
}
