package com.schmitt.reactive.java.src.controller;

import com.schmitt.reactive.java.src.model.CharProcessorResult;
import com.schmitt.reactive.java.src.service.record.processor.character.blocking.FluxCharBlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.character.blocking.MonoCharBlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.character.nonblocking.FluxCharNonBlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.character.nonblocking.MonoCharNonBlockingRecordProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/record/processor")
public class RecordProcessorController {
    private final Random randomGenerator;
    private final MonoCharBlockingRecordProcessor monoCharBlockingRecordProcessor;
    private final MonoCharNonBlockingRecordProcessor monoCharNonBlockingRecordProcessor;
    private final FluxCharBlockingRecordProcessor fluxCharBlockingRecordProcessor;
    private final FluxCharNonBlockingRecordProcessor fluxCharNonBlockingRecordProcessor;

    @GetMapping(value = "/blocking/character/mono", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<CharProcessorResult>> getResultsFromBlockingMono() {
        final int numberOfRecordsToProduce = randomGenerator.nextInt(50);
        final int numberOfCharactersPerRecord = randomGenerator.nextInt(200);
        final int numberOfTimesToProcess = randomGenerator.nextInt(100);

        return Mono.fromCallable(() -> monoCharBlockingRecordProcessor.produceResult(numberOfRecordsToProduce, numberOfCharactersPerRecord))
                .publishOn(Schedulers.boundedElastic())
                .repeat(numberOfTimesToProcess - 1)
                .collectList();
    }

    @GetMapping(value = "/blocking/character/flux", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<List<CharProcessorResult>> getResultsFromBlockingFlux() {
        final int numberOfRecordsToProduce = randomGenerator.nextInt(50);
        final int numberOfCharactersPerRecord = randomGenerator.nextInt(200);
        final int numberOfTimesToProcess = randomGenerator.nextInt(100);

        return Mono.fromCallable(() -> fluxCharBlockingRecordProcessor.produceResult(numberOfRecordsToProduce, numberOfCharactersPerRecord))
                .publishOn(Schedulers.boundedElastic())
                .repeat(numberOfTimesToProcess - 1)
                .collectList();
    }

    @GetMapping(value = "/nonblocking/character/mono", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<CharProcessorResult>> getResultsFromNonBlockingMono() {
        final int numberOfRecordsToProduce = randomGenerator.nextInt(50);
        final int numberOfCharactersPerRecord = randomGenerator.nextInt(200);
        final int numberOfTimesToProcess = randomGenerator.nextInt(100);

        return monoCharNonBlockingRecordProcessor.produceResult(numberOfRecordsToProduce, numberOfCharactersPerRecord)
                .repeat(numberOfTimesToProcess - 1)
                .collectList();
    }

    @GetMapping(value = "/nonblocking/character/flux", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<CharProcessorResult> getResultsFromNonBlockingFlux() {
        final int numberOfRecordsToProduce = randomGenerator.nextInt(50);
        final int numberOfCharactersPerRecord = randomGenerator.nextInt(200);
        final int numberOfTimesToProcess = randomGenerator.nextInt(100);

        return fluxCharNonBlockingRecordProcessor.produceResult(numberOfRecordsToProduce, numberOfCharactersPerRecord)
                .repeat(numberOfTimesToProcess - 1);
    }
}
