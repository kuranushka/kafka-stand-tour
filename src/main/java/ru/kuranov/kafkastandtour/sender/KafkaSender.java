package ru.kuranov.kafkastandtour.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaSender {

    private final KafkaTemplate<String, String> template;
    private  RoutingKafkaTemplate routingTemplate;

    Logger log = LoggerFactory.getLogger(KafkaSender.class);


    public KafkaSender(KafkaTemplate<String, String> template) {
        this.template = template;
    }

    public void sendMessage(String topicName, String message) {
        ListenableFuture<SendResult<String, String>> future = template.send(topicName, message);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onFailure(Throwable ex) {

            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("MESSAGE \"{}\" FROM \"{}\" WAS RECEIVED WITH OFFSET {} PARTITION {} KEY {}",
                        result.getProducerRecord().value(),
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().offset(),
                        result.getRecordMetadata().partition(),
                        result.getProducerRecord().key());
            }
        });
    }
}
