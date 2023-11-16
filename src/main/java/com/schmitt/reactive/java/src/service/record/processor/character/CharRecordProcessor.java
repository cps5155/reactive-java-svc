package com.schmitt.reactive.java.src.service.record.processor.character;

import com.schmitt.reactive.java.src.model.CharProcessorRecord;
import com.schmitt.reactive.java.src.model.CharProcessorResult;
import com.schmitt.reactive.java.src.service.record.processor.BlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.RecordProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public abstract class CharRecordProcessor extends RecordProcessor<CharProcessorRecord, CharProcessorResult, Character> {
    private final Random randomGenerator;

    @Override
    protected Mono<List<CharProcessorRecord>> getMonoToProcess(int numberOfRecordsToProduce, int numberOfAttributesPerRecord) {
        return Mono.fromCallable(() -> IntStream.rangeClosed(0, numberOfRecordsToProduce)
                .boxed()
                .map(counter -> {
                    final List<Character> randomCharacters = IntStream.rangeClosed(0, numberOfAttributesPerRecord)
                            .boxed()
                            .map(charIndex -> (char) (randomGenerator.nextInt(26) + 'a'))
                            .toList();
                    return new CharProcessorRecord(UUID.randomUUID(), counter, randomCharacters);
                })
                .toList());
    }

    @Override
    protected CharProcessorResult createResult(@NonNull Instant beginProcessingTs, @NonNull Instant endProcessingTs,
                                               @NonNull Duration processingDuration, int numberOfRecordsToProduce,
                                               @NonNull Map<Character, Map<UUID, Long>> resultMap) {
        return new CharProcessorResult(beginProcessingTs, endProcessingTs,
                processingDuration, numberOfRecordsToProduce, resultMap);
    }
}
