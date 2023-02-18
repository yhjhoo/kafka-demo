package me.prince.kafkademo;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.filter.TypeExcludeFilters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.Properties;

@SpringBootTest
@MockBean({Listener.class})
class KafkaDemoApplicationTests {

    @Test
    void contextLoads() {
        /*Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "prince-app");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

        final StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> source = builder.stream("streams-plaintext-input");

        source.to("streams-pipe-output");
//        builder.stream("streams-plaintext-input").to("streams-pipe-output");
        final Topology topology = builder.build();

        System.out.println(topology.describe());*/

    }


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void writeSample() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            Thread.sleep(3000);
            String data = "hello_" + i + "_" + LocalDateTime.now();
            System.out.println("data: " + data);
            kafkaTemplate.send("quickstart-events1", "m_" + i, data);
        }
    }

//    @Test
//    void read() {
//        kafkaTemplate.
//    }

}
