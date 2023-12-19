package com.schmitt.reactive.java.src.model.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor
@Accessors(chain = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Person implements Serializable {
    protected static final String FIELDS = Arrays.stream(Person.class.getDeclaredFields())
            .map(field -> String.join(":", field.getName(), field.getType().getSimpleName()))
            .collect(Collectors.joining(","));
    //@Getter private static final UUID VERSION_IDENTIFIER = UUID.fromString(FIELDS);
    @NonNull private final String name;
    private final int age;
    private Long phoneNumber;

    public Optional<Long> getPhoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }
}
