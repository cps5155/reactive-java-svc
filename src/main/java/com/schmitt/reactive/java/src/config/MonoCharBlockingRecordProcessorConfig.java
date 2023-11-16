package com.schmitt.reactive.java.src.config;

import com.schmitt.reactive.java.src.service.record.processor.character.blocking.FluxCharBlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.character.blocking.MonoCharBlockingRecordProcessor;
import org.springframework.context.annotation.Bean;

import java.util.Random;

public class MonoCharBlockingRecordProcessorConfig {
    @Bean
    public MonoCharBlockingRecordProcessor getMonoCharBlockingRecordProcessor(Random random) {
        return new MonoCharBlockingRecordProcessor(random);
    }
}
