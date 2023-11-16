package com.schmitt.reactive.java.src.service.record.processor;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface NonBlockingRecordProcessor<P, R, T> {

    /**
     * Given a Mono of List of MyRecordObject, produce a map of every character that is in any
     * MyRecordObject.charactersToProcess processed whose value includes a Map.Entry representing
     * which MyRecordObject.objectUuid has that character & how many times that character is seen
     * in that MyRecordObject.
     * e.g.
     * {
     *  'a', [ {"my-uuid-1234-...", 3}, {"my-uuid-5678-...", 1} ],
     *  'b', [ {"my-uuid-5678-...", 43}],
     *  'f', [ {"my-uuid-1234-...", 1}, {"my-uuid-5678-...", 10}, {"my-uuid-9101-...", 10} ],
     * }
     */
    Mono<Map<T, Map<UUID, Long>>> processRecords(Mono<List<P>> recordsToProcess);

    Mono<R> produceResult(int numberOfRecordsToProduce, int numberOfCharactersPerRecord);
}
