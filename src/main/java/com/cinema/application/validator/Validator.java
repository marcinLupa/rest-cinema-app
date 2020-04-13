package com.cinema.application.validator;

import java.util.Map;

public interface Validator<T> {
    Map<String, String> validate(T item);
    boolean hasErrors();
    String getExceptionMessage();
}
