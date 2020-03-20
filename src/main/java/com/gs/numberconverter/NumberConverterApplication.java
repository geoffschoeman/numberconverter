package com.gs.numberconverter;

import com.gs.numberconverter.converter.number.NumberToStringConverter;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Main application class sets up the SpringBoot configuration automatically.
 */
@SpringBootApplication
public class NumberConverterApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(NumberConverterApplication.class);
    private static final String INVALID_NUMBER_ERROR = "Unable to parse input paramater. Only valid integer values are accepted";

    /**
     * Main method only executed in standalone mode. If a valid integer is passed as an argument the text representation
     * will print to the console.
     * @param args One or many numbers to convert to text
     */
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(NumberConverterApplication.class, args);

        Stream.of(args).forEach(number -> {
            try {
                LOGGER.info(context.getBean(NumberToStringConverter.class).convert(Integer.valueOf(number)));

            } catch (NumberFormatException e) {
                LOGGER.error(INVALID_NUMBER_ERROR, e);
            }
        });
    }
}
