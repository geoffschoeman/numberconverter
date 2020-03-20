package com.gs.numberconverter.converter;

/**
 * Interface definition for conversion classes.
 * @param <T> The input value
 * @param <R> The output value
 */
public interface Converter<T, R> {

    /**
     * Converts the given T value to R
     * @param value the value to convert
     * @return the converted value
     */
    R convert(T value);
}
