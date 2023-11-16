package com.schmitt.reactive.java.src.config;

import com.schmitt.reactive.java.src.service.record.processor.character.blocking.MonoCharBlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.character.nonblocking.MonoCharNonBlockingRecordProcessor;
import org.springframework.context.annotation.Bean;

import java.util.Random;

public class MonoCharNonBlockingRecordProcessorConfig {
    @Bean
    public MonoCharNonBlockingRecordProcessor getMonoCharNonBlockingRecordProcessor(Random random) {
        return new MonoCharNonBlockingRecordProcessor(random);
    }
}
