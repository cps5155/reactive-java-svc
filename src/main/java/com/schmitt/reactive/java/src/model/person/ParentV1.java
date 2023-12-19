package com.schmitt.reactive.java.src.model.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Setter
@Getter
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParentV1 extends Person implements Serializable {
    private static final String THIS_FIELDS = Arrays.stream(ParentV1.class.getDeclaredFields())
            .map(field -> String.join(field.getName(), field.getType().getSimpleName()))
            .collect(Collectors.joining(","));
    //private static final UUID VERSION_IDENTIFIER = UUID.fromString(String.join(",", Person.getVERSION_IDENTIFIER().toString(), THIS_FIELDS));
    private Set<Child> children;
    public ParentV1(@NonNull String name, int age) {
        super(name, age);
    }

    public Set<Child> getChildren() {
        return Optional.ofNullable(children).orElse(Collections.emptySet());
    }
}

