package com.schmitt.reactive.java.src.config;

import com.schmitt.reactive.java.src.controller.RecordProcessorController;
import com.schmitt.reactive.java.src.service.record.processor.character.blocking.FluxCharBlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.character.blocking.MonoCharBlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.character.nonblocking.FluxCharNonBlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.character.nonblocking.MonoCharNonBlockingRecordProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Random;

@Import({
        FluxCharBlockingRecordProcessorConfig.class,
        FluxCharNonBlockingRecordProcessorConfig.class,
        MonoCharBlockingRecordProcessorConfig.class,
        MonoCharNonBlockingRecordProcessorConfig.class
})
public class RecordProcessControllerConfig {
    @Bean
    public Random randomGenerator() {
        return new Random();
    }

    @Bean
    public RecordProcessorController recordProcessorController(Random randomGenerator,
                                                               MonoCharBlockingRecordProcessor monoCharBlockingRecordProcessor,
                                                               FluxCharBlockingRecordProcessor fluxCharBlockingRecordProcessor,
                                                               MonoCharNonBlockingRecordProcessor monoCharNonBlockingRecordProcessor,
                                                               FluxCharNonBlockingRecordProcessor fluxCharNonBlockingRecordProcessor) {
        return new RecordProcessorController(randomGenerator, monoCharBlockingRecordProcessor,
                monoCharNonBlockingRecordProcessor,fluxCharBlockingRecordProcessor, fluxCharNonBlockingRecordProcessor);
    }
}
