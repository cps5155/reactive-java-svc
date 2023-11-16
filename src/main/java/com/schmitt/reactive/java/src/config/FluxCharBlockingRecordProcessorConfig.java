package com.schmitt.reactive.java.src.config;

import com.schmitt.reactive.java.src.service.record.processor.character.blocking.FluxCharBlockingRecordProcessor;
import org.springframework.context.annotation.Bean;

import java.util.Random;

public class FluxCharBlockingRecordProcessorConfig {
    @Bean
    public FluxCharBlockingRecordProcessor getFluxCharBlockingRecordProcessor(Random random) {
        return new FluxCharBlockingRecordProcessor(random);
    }
}
