package com.schmitt.reactive.java.src.service.record.processor.character;

import com.schmitt.reactive.java.src.model.CharProcessorResult;
import com.schmitt.reactive.java.src.service.record.processor.character.blocking.FluxCharBlockingRecordProcessor;
import com.schmitt.reactive.java.src.service.record.processor.character.blocking.MonoCharBlockingRecordProcessor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests efficiency of mono vs flux stream processing of objects.
 */
class MonoVsFluxCharBlockingRecordProcessorTest {
    private final Random randomGenerator = new Random();
    @ParameterizedTest
    @CsvSource({
            //"1",
            //"10",
            "100",
            //"1000",
    })
    void blockingComparison(int numberOfResultsToProduce) {
        // arrange
        final int numberOfRecordsToProduce = 10;
        final int numberOfCharactersPerRecord = 200;
        final MonoCharBlockingRecordProcessor monoRecordProcessor = new MonoCharBlockingRecordProcessor(randomGenerator);
        final FluxCharBlockingRecordProcessor fluxRecordProcessor = new FluxCharBlockingRecordProcessor(randomGenerator);

        // when
        final List<CharProcessorResult> monoResult = Mono.fromCallable(() -> monoRecordProcessor.produceResult(numberOfRecordsToProduce, numberOfCharactersPerRecord))
                .repeat(numberOfResultsToProduce)
                .collectList()
                .blockOptional()
                .orElse(Collections.emptyList());
        final List<CharProcessorResult> fluxResult = Mono.fromCallable(() -> fluxRecordProcessor.produceResult(numberOfRecordsToProduce, numberOfCharactersPerRecord))
                .repeat(numberOfResultsToProduce)
                .collectList()
                .blockOptional()
                .orElse(Collections.emptyList());

        // assert
        assertThat(fluxResult)
                .isNotEmpty()
                .hasSameSizeAs(monoResult);
        final Long monoIsFaster = monoResult
                .stream()
                .map(CharProcessorResult::processingDuration)
                .filter(mDuration -> fluxResult.stream().map(CharProcessorResult::processingDuration).anyMatch(fDuration -> mDuration.compareTo(fDuration) < 0))
                .count();
        final Long fluxIsFaster = fluxResult
                .stream()
                .map(CharProcessorResult::processingDuration)
                .filter(fDuration -> monoResult.stream().map(CharProcessorResult::processingDuration).anyMatch(mDuration -> fDuration.compareTo(mDuration) < 0))
                .count();
        // original assertions based on assumption that Mono processing would be faster than Flux
        /*assertThat(monoIsFaster.intValue())
                .as("Mono is faster more often than flux")
                .isGreaterThan(fluxIsFaster.intValue());
        assertThat(fluxIsFaster)
                .as("Flux is never faster than Mono")
                .isZero();*/
    }


    @ParameterizedTest
    @CsvSource({
            //"1",
            //"10",
            "100",
            //"1000",
    })
    void blockingComparisonTuple(int numberOfResultsToProduce) {
        // arrange
        final int numberOfRecordsToProduce = 10;
        final int numberOfCharactersPerRecord = 200;
        final MonoCharBlockingRecordProcessor monoRecordProcessor = new MonoCharBlockingRecordProcessor(randomGenerator);
        final FluxCharBlockingRecordProcessor fluxRecordProcessor = new FluxCharBlockingRecordProcessor(randomGenerator);

        // when
        final List<Tuple2<CharProcessorResult, CharProcessorResult>> resultTuple = Mono.zip(
                Mono.fromCallable(() -> monoRecordProcessor.produceResult(numberOfRecordsToProduce, numberOfCharactersPerRecord)),
                        Mono.fromCallable(() -> fluxRecordProcessor.produceResult(numberOfRecordsToProduce, numberOfCharactersPerRecord)))
                .repeat(numberOfResultsToProduce - 1)
                .collectList()
                .blockOptional()
                .orElse(Collections.emptyList());

        // assert
        assertThat(resultTuple)
                .isNotEmpty();
        final Map<String, Long> fasterMap = resultTuple
                .stream()
                .map(result -> result.getT1().processingDuration().compareTo(result.getT2().processingDuration()) < 0
                        ? Map.entry("mono", result.getT1().processingDuration())
                        : Map.entry("flux", result.getT2().processingDuration()))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.counting()));
        final int monoIsFaster = Optional.ofNullable(fasterMap.get("mono"))
                .map(Long::intValue)
                .orElse(0);
        final int fluxIsFaster = Optional.ofNullable(fasterMap.get("flux"))
                .map(Long::intValue)
                .orElse(0);
        // original assertions based on assumption that Mono processing would be faster than Flux
        /*assertThat(monoIsFaster)
                .as("Mono is faster more often than flux")
                .isGreaterThan(fluxIsFaster);
        assertThat(fluxIsFaster)
                .as("Flux is never faster than Mono")
                .isZero();*/
    }
}
