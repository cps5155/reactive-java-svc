package com.schmitt.reactive.java.src.model.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Setter
@Getter
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Parent extends Person implements Serializable {
    private Set<Child> children;
    public Parent(@NonNull String name, int age) {
        super(name, age);
    }

    public Set<Child> getChildren() {
        return Optional.ofNullable(children).orElse(Collections.emptySet());
    }
}
