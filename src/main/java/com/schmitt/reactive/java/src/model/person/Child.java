package com.schmitt.reactive.java.src.model.person;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Optional;

@Setter
@Getter
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Child extends Person implements Serializable {
    private Integer grade;
    public Child(@NonNull String name, int age) {
        super(name, age);
    }

    public Optional<Integer> getGrade() {
        return Optional.ofNullable(grade);
    }
}
