package com.schmitt.reactive.java.src.service.record.processor.character.blocking;

import com.schmitt.reactive.java.src.model.CharProcessorRecord;
import com.schmitt.reactive.java.src.model.CharProcessorResult;
import com.schmitt.reactive.java.src.service.record.processor.BlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.character.CharRecordProcessor;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public abstract class CharBlockingRecordProcessor extends CharRecordProcessor
        implements BlockingRecordProcessor<CharProcessorRecord, CharProcessorResult, Character> {
    protected CharBlockingRecordProcessor(Random randomGenerator) {
        super(randomGenerator);
    }

    @Override
    public CharProcessorResult produceResult(int numberOfRecordsToProduce, int numberOfCharactersPerRecord) {
        final Mono<List<CharProcessorRecord>> recordObjects = getMonoToProcess(numberOfRecordsToProduce, numberOfCharactersPerRecord);
        final Instant beginProcessing = Instant.now();

        final Map<Character, Map<UUID, Long>> resultMap = processRecords(recordObjects);

        final Instant endProcessing = Instant.now();
        final Duration totalProcessingTime = Duration.between(beginProcessing, endProcessing);

        return createResult(beginProcessing, endProcessing, totalProcessingTime, numberOfRecordsToProduce, resultMap);
    }
}
