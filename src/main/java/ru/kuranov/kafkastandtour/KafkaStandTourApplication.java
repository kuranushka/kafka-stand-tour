package ru.kuranov.kafkastandtour;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import ru.kuranov.kafkastandtour.sender.KafkaSender;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class KafkaStandTourApplication {

    @Value("${topic1}")
    private String topic1;

    @Value("${topic2}")
    private String topic2;

    @Value("${message1}")
    private String message1;

    @Value("${message2}")
    private String message2;


    public static void main(String[] args) {
        SpringApplication.run(KafkaStandTourApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner(KafkaSender sender) {
        return args -> {
            sender.sendMessage(topic1, message1);
            sender.sendMessage(topic2, message2);
        };
    }

}
