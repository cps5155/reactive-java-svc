package com.schmitt.reactive.java.src.model;

import org.springframework.lang.NonNull;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record CharProcessorResult(@NonNull Instant startTime, @NonNull Instant endTime, @NonNull Duration processingDuration,
                                  int recordsProcessed, @NonNull Map<Character, Map<UUID, Long>> resultMap) {
}
