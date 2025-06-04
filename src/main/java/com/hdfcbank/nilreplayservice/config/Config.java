package com.hdfcbank.nilreplayservice.config;

import com.hdfcbank.messageconnect.config.MessageProducerConfig;
import com.hdfcbank.messageconnect.producer.KafkaProducerFactory;
import com.hdfcbank.messageconnect.producer.MessageErrorHandler;
import com.hdfcbank.messageconnect.producer.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class Config {

    @Bean(name = "messageSenderKafka")
    MessageSender messageSenderKafka(KafkaProperties kafkaProperties) throws Exception {

        MessageErrorHandler errorHandler = (msg, exception) -> {
            //TODO: Recovery logic of msg to directly persist to database.(AUDIT TABLE)
            log.error("Failed in publishing the message to the response topic", exception);

        };

        var msgProducerConfig = MessageProducerConfig.builder()
                .errorHandler(errorHandler)
                .properties(kafkaProperties)
                //.metricsConfig(MessageMetricsConfig.builder().isPublishMetrics(loggingEnable).metricsInterval(kafkaMetricInterval).build())
                .build();

        return KafkaProducerFactory.createKafkaProducer(msgProducerConfig);

    }
}
