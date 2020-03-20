package com.gs.numberconverter.converter.number;

import com.gs.numberconverter.converter.Converter;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Converter implementation for converting numeric values to text.
 */
@Service
public class NumberToStringConverter implements Converter<Integer, String> {
    
    private static final String BILLION_LABEL = " billion";
    private static final String MILLION_LABEL = " million";
    private static final String THOUSAND_LABEL = " thousand";
    private static final String HUNDRED_LABEL = " hundred";
    private static final String NEGATIVE_LABEL = "negative";
    private static final String ZERO_LABEL = "Zero";
    private static final String EMPTY_STRING = "";
    private static final String AND = " and";
    private static final Long BILLION = 1000000000L;
    private static final Long MILLION = 1000000L;
    private static final Long THOUSAND = 1000L;
    private static final Long HUNDRED = 100L;
    private static final Long TWENTY = 20L;
    private static final Long TEN = 10L;
    private static final Long ZERO = 0L;

    /**
     * Map for retrieving the ones position labels.
     * Injected from: src/main/resources/application-conversion.map.properties
     */
    @Value("#{${number.conversion.position.map.ones}}")
    private Map<Long, String> ones;

    /**
     * Map for retrieving the position labels for 10 - 19.
     * Injected from: src/main/resources/application-conversion.map.properties
     */
    @Value("#{${number.conversion.position.map.teens}}")
    private Map<Long, String> teens;

    /**
     * Map for retrieving the position labels for the multiples of 10.
     * Injected from: src/main/resources/application-conversion.map.properties
     */
    @Value("#{${number.conversion.position.map.tens}}")
    private Map<Long, String> tens;
    
    /**
     * Private method converts the given number to text.
     * Example:
     *   The number 2147483647
     *   will return the string:
     *   Two billion one hundred forty seven million four hundred eighty three thousand six hundred forty seven
     * @param number A valid integer
     * @throws IllegalArgumentException if a null value is passed
     * @return the text string
     */
    @Override
    public String convert(Integer number) {
        
        if (number == null) {
            throw new IllegalArgumentException("Number cannot be null");
        }
        StringBuilder result = new StringBuilder();
        
        // Conversion to Long is necessary in the case when Integer.MIN_VALUE 
        // is passed in as it can't be converted to a positive integer.
        Long numberAsLong = Long.valueOf(number);

        if (ZERO.compareTo(numberAsLong) > 0) {
            result.append(NEGATIVE_LABEL);
            numberAsLong = Math.abs(numberAsLong);
        }
        return capitalizeFirstLetterOfString(result.append(convertPositiveNumberToString(numberAsLong)).toString());
    }

    /**
     * Private method converts the given positive number to text.
     * @param number A positive number
     * @throws IllegalArgumentException if a negative number is passed.
     * @return 
     */
    private String convertPositiveNumberToString(Long number) {

        if (ZERO.compareTo(number) > 0) {
            throw new IllegalArgumentException("Must be a positive number");
        }

        if (ZERO.compareTo(number) == 0) {
            return ZERO_LABEL;
        }

        if (number.compareTo(BILLION) > -1) {
            return calculateAndConvertToString(number, BILLION, BILLION_LABEL);
        }

        if (number.compareTo(MILLION) > -1) {
            return calculateAndConvertToString(number, MILLION, MILLION_LABEL);
        }

        if (number.compareTo(THOUSAND) > -1) {
            return calculateAndConvertToString(number, THOUSAND, THOUSAND_LABEL);
        }
        return hundredsToString(number);
    }

    /**
     * Private convenience method calculates the number to append with the given name and converts and appends the remainder.
     * @param number The full number to parse
     * @param divisor The divisor for the numeric place you want to parse.
     * @param name The return label for the numeric place.
     * @return 
     */
    private String calculateAndConvertToString(Long number, Long divisor, String name) {
        Long hundreds = number / divisor;
        return String.join(EMPTY_STRING, hundredsToString(hundreds), name, convertPositiveNumberToString(number - (hundreds * divisor)));
    }

    /**
     * Private method converts a one to three digit number to a string. Or an empty string if a zero value is passed.
     * Example:
     *   The number 983
     *   Will return the string:
     *   Nine hundred eighty three
     * @param number The number to be converted
     * @throws IllegalArgumentException if the given number is out of the 0 to 999 range.
     * @throws IllegalStateException if the internal lookup maps cannot map the given value.
     * @return 
     */
    private String hundredsToString(Long number) {

        if (number == null || number.compareTo(ZERO) < 0 || number.compareTo(THOUSAND) > -1) {
            throw new IllegalArgumentException("Must be a number from 0 to 999");
        }

        if (number.compareTo(ZERO) == 0) {
            return EMPTY_STRING;
        }

        if (number.compareTo(HUNDRED) >= 0) {
            Long numberOfHundreds = number / HUNDRED;
            Long remainder = number - (numberOfHundreds * HUNDRED);
            return String.join(EMPTY_STRING, ones.get(numberOfHundreds), HUNDRED_LABEL, remainder.compareTo(ZERO) > 0 ? AND : EMPTY_STRING, hundredsToString(remainder));
        }

        if (number.compareTo(TWENTY) >= 0) {
            Long numberOfTens = number / TEN;
            return String.join(EMPTY_STRING, tens.get(numberOfTens), hundredsToString(number - (numberOfTens * TEN)));
        }
        
        if (teens.containsKey(number)) {
            return teens.get(number);
        }
        return Optional.ofNullable(ones.get(number))
                .orElseThrow(() -> new IllegalStateException("Unable to locate text mapping for numeric value: " + number));
    }
    
    /**
     * Private method returns the given string with the first character capitalized.
     * @param value The string to convert
     * @return 
     */
    private String capitalizeFirstLetterOfString(String value) {
        return Optional.ofNullable(value)
                .map(String::trim)
                .filter(v -> !v.isEmpty())
                .map(v -> v.substring(0, 1).toUpperCase() + v.substring(1))
                .orElse(value);
    }
}
