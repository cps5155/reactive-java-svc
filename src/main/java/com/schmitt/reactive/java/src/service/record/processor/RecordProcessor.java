package com.schmitt.reactive.java.src.service.record.processor;

import org.springframework.lang.NonNull;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class RecordProcessor<P, R, T> {
    /**
     * Create a Mono which will be used to test the actual processing efficiency.
     */
    protected abstract Mono<List<P>> getMonoToProcess(int numberOfRecordsToProduce, int numberOfAttributesPerRecord);

    /**
     * Given the result pieces of a test create a result object.
     */
    protected abstract R createResult(@NonNull Instant beginProcessingTs, @NonNull Instant endProcessingTs, @NonNull Duration processingDuration,
                                      int numberOfRecordsToProduce,
                                      @NonNull Map<T, Map<UUID, Long>> resultMap);
}
