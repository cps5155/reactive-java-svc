package com.schmitt.reactive.java.src.service.record.processor.character.nonblocking;

import com.schmitt.reactive.java.src.model.CharProcessorRecord;
import com.schmitt.reactive.java.src.service.record.processor.character.blocking.CharBlockingRecordProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implement CharRecordProcessor in a flux-esq effort.
 * i.e. turn the result of the mono into a reactive stream in order
 * to complete the processing
 */
public class FluxCharNonBlockingRecordProcessor extends CharNonBlockingRecordProcessor {
    public FluxCharNonBlockingRecordProcessor(Random randomGenerator) {
        super(randomGenerator);
    }

    /**
     * Given a Mono of List of MyRecordObject, produce a map of every character that is in any
     * MyRecordObject.charactersToProcess processed whose value includes a Map.Entry representing
     * which MyRecordObject.objectUuid has that character & how many times that character is seen
     * in that MyRecordObject.
     * e.g.
     * {
     * 'a', [ {"my-uuid-1234-...", 3}, {"my-uuid-5678-...", 1} ],
     * 'b', [ {"my-uuid-5678-...", 43}],
     * 'f', [ {"my-uuid-1234-...", 1}, {"my-uuid-5678-...", 10}, {"my-uuid-9101-...", 10} ],
     * }
     *
     * @param recordsToProcess
     */
    @Override
    public Mono<Map<Character, Map<UUID, Long>>> processRecords(Mono<List<CharProcessorRecord>> recordsToProcess) {
        return recordsToProcess
                .flatMapMany(recordList -> Flux.fromStream(recordList.stream()))
                .flatMap(recordObject -> Flux.fromStream(recordObject.charactersToProcess()
                        .stream()
                        .map(charToProcess -> Map.entry(charToProcess, recordObject.objectUuid()))))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.groupingBy(Map.Entry::getValue, Collectors.counting())));
    }

}
