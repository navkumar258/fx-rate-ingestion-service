package com.example.fx.rate.ingestion.service.service;

import com.example.fx.rate.ingestion.service.model.FXRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerService.class);

  @Value(value = "${spring.kafka.topic.fx-rates}")
  private String fxRatesTopic;

  private final KafkaTemplate<String, FXRate> kafkaTemplate;

  @Autowired
  public KafkaProducerService(KafkaTemplate<String, FXRate> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void sendMessage(FXRate fxRate) {
    CompletableFuture<SendResult<String, FXRate>> future = kafkaTemplate.send(fxRatesTopic, fxRate.currencyPair(), fxRate);

    future.whenComplete((result, throwable) -> {
      if (throwable != null) {
        // handle failure
        LOGGER.info("Unable to send message: [{}] due to: [{}]", fxRate, throwable.getMessage());
      } else {
        // handle success
        LOGGER.info("Sent message: [{}] with offset: [{}]", fxRate, result.getRecordMetadata().offset());
      }
    });
  }
}
