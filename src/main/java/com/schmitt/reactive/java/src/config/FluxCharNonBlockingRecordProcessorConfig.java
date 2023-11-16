package com.schmitt.reactive.java.src.config;

import com.schmitt.reactive.java.src.service.record.processor.character.blocking.FluxCharBlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.character.nonblocking.FluxCharNonBlockingRecordProcessor;
import org.springframework.context.annotation.Bean;

import java.util.Random;

public class FluxCharNonBlockingRecordProcessorConfig {
    @Bean
    public FluxCharNonBlockingRecordProcessor getFluxCharNonBlockingRecordProcessor(Random random) {
        return new FluxCharNonBlockingRecordProcessor(random);
    }
}
