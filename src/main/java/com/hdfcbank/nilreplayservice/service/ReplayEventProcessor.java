package com.hdfcbank.nilreplayservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdfcbank.nilreplayservice.kafkaproducer.KafkaUtils;
import com.hdfcbank.nilreplayservice.model.MsgEventTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.hdfcbank.nilreplayservice.utils.Constants.*;

@Service
public class ReplayEventProcessor {

    @Autowired
    private KafkaUtils kafkaUtils;

    @Value("${topic.sfmstopic}")
    private String sfmsTopic;

    @Value("${topic.fctopic}")
    private String fcTopic;

    @Value("${topic.ephtopic}")
    private String ephTopic;

    @Autowired
    ObjectMapper mapper;

    public void processFailedEvents(MsgEventTracker event) {
        try {

            String xml = event.getIntermediateReq();
            String target = event.getTarget();

            if (target.equalsIgnoreCase(FC)) {
                kafkaUtils.publishToResponseTopic(xml, fcTopic);

            }
           else if (target.equalsIgnoreCase(EPH)) {
                kafkaUtils.publishToResponseTopic(xml, ephTopic);
            }
            else if (target.equalsIgnoreCase(SFMS)) {
                kafkaUtils.publishToResponseTopic(xml, sfmsTopic);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void processReceivedEvents(MsgEventTracker event) {
        try {

            String xml = event.getOrgnlReq();

            kafkaUtils.publishToResponseTopic(xml, "SFMS2TOPIC");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
