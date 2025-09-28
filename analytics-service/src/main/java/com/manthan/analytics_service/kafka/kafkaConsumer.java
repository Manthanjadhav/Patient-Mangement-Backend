package com.manthan.analytics_service.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
public class kafkaConsumer {
    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumerEvent(byte[] event){
        try {
            PatientEvent patientEvent = PatientEvent.parseFrom(event);
            log.info("PatientEvent consumed :{}", patientEvent.toString());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error Deserializing event :{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
