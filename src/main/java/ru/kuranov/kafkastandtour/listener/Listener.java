package ru.kuranov.kafkastandtour.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    Logger log = LoggerFactory.getLogger(Listener.class);

    @KafkaListener(topics = "topic2", groupId = "myGroup")
    public void listenTopic1(String message) {
        log.info(message);
    }

    @KafkaListener(topics = "topic1", groupId = "myGroup")
    public void listenTopic2(String message) {
        log.info(message);
    }


    @KafkaListener(
            groupId = "myGroup",
            topicPartitions = @TopicPartition(
                    topic = "topic1",
                    partitionOffsets = {@PartitionOffset(
                            partition = "0",
                            initialOffset = "0")}))
    void listenPartitionWithOffset(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.OFFSET) int offset) {
        log.info("MESSAGE - {} PARTITION - {} OFFSET - {}", message, partition, offset);
    }
}
