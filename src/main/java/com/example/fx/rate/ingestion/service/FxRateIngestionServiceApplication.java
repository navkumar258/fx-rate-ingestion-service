package com.example.fx.rate.ingestion.service;

import com.example.fx.rate.ingestion.service.model.FXRate;
import com.example.fx.rate.ingestion.service.model.FXRateRaw;
import com.example.fx.rate.ingestion.service.service.KafkaProducerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@SpringBootApplication
public class FxRateIngestionServiceApplication {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private final KafkaProducerService kafkaProducerService;

	@Autowired
	FxRateIngestionServiceApplication(KafkaProducerService kafkaProducerService) {
		this.kafkaProducerService = kafkaProducerService;
	}

	public static void main(String[] args) {
		SpringApplication.run(FxRateIngestionServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("fx_rates.json")) {
				List<FXRateRaw> fxRates = OBJECT_MAPPER.readValue(inputStream, new TypeReference<List<FXRateRaw>>() {
				});

				for (FXRateRaw fxRateRaw : fxRates) {
					FXRate fxRate = new FXRate(
									fxRateRaw.symbol(),
									fxRateRaw.bid(),
									fxRateRaw.ask(),
									fxRateRaw.bid().add(fxRateRaw.ask()).divide(BigDecimal.TWO, 4, RoundingMode.HALF_UP),
									fxRateRaw.timestamp());

					this.kafkaProducerService.sendMessage(fxRate);
				}
			}
		};
	}
}
