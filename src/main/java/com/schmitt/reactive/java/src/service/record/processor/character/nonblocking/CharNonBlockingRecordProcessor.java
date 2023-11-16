package com.schmitt.reactive.java.src.service.record.processor.character.nonblocking;

import com.schmitt.reactive.java.src.model.CharProcessorRecord;
import com.schmitt.reactive.java.src.model.CharProcessorResult;
import com.schmitt.reactive.java.src.service.record.processor.BlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.NonBlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.character.CharRecordProcessor;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public abstract class CharNonBlockingRecordProcessor extends CharRecordProcessor
        implements NonBlockingRecordProcessor<CharProcessorRecord, CharProcessorResult, Character> {
    protected CharNonBlockingRecordProcessor(Random randomGenerator) {
        super(randomGenerator);
    }

    @Override
    public Mono<CharProcessorResult> produceResult(int numberOfRecordsToProduce, int numberOfCharactersPerRecord) {
        final Mono<List<CharProcessorRecord>> recordObjects = getMonoToProcess(numberOfRecordsToProduce, numberOfCharactersPerRecord);
        final Instant beginProcessing = Instant.now();

        return processRecords(recordObjects)
                .map(resultMap -> {
                    final Instant endProcessing = Instant.now();
                    final Duration totalProcessingTime = Duration.between(beginProcessing, endProcessing);

                    return createResult(beginProcessing, endProcessing, totalProcessingTime, numberOfRecordsToProduce, resultMap);
                });
    }
}
