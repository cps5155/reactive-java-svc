package com.schmitt.reactive.java.src.model;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public record CharProcessorRecord(@NonNull UUID objectUuid, int objectCountIndex, @NonNull List<Character> charactersToProcess) {
}
