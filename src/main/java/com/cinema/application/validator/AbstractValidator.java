package com.cinema.application.validator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractValidator<T> implements Validator<T> {

    protected final Map<String, String> errors = new HashMap<>();

    @Override
    public boolean hasErrors() {

        return !errors.isEmpty();
    }

    @Override
    public String getExceptionMessage() {
            return errors
                    .entrySet()
                    .stream()
                    .map(k-> k.getKey()+" - "+k.getValue())
                    .collect(Collectors.joining());

    }
}
