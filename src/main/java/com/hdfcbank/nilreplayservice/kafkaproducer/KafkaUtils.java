package com.hdfcbank.nilreplayservice.kafkaproducer;

import com.hdfcbank.messageconnect.config.PubSubOptions;
import com.hdfcbank.messageconnect.dapr.producer.DaprProducer;
import com.hdfcbank.messageconnect.producer.MessageSender;
import com.hdfcbank.nilreplayservice.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class KafkaUtils {

    @Autowired
    DaprProducer daprProducer;


    public void publishToResponseTopic(String message, String topic) {

        var kafkaBinding = PubSubOptions.builder().requestData(message).topic(topic)
                .pubsubName(Constants.KAFKA_RESPONSE_TOPIC_DAPR_BINDING).build();
        var resp = daprProducer.invokeDaprPublishEvent(kafkaBinding);
        resp.doOnSuccess(res -> {
            log.info("Response published to response topic successfully");
        }).onErrorResume(res -> {
            log.info("Error on publishing the response to response topic");
            return Mono.empty();
        }).share().block();

    }

    @Autowired
    private MessageSender msgSenderKafka;


    public void sendMessage(String xmlMessage) {

        msgSenderKafka.send("FCTOPIC", xmlMessage);
        log.info("Message send in kafka topic ({})", "Neft_Pacs008_Topic");

    }
}
