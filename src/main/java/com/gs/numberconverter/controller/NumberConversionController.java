package com.gs.numberconverter.controller;

import com.gs.numberconverter.converter.number.NumberToStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller defines the number conversion endpoints.
 */
@Controller
public class NumberConversionController {

    @Autowired
    private NumberToStringConverter converter;

    /**
     * Returns the number conversion view.
     * @return 
     */
    @RequestMapping("/")
    public String main() {
        return "numberconversion.html";
    }

    /**
     * Converts the given number to English text.
     * @param number
     * @return 
     */
    @RequestMapping("/convert/{number}")
    public @ResponseBody String numberToString(@PathVariable("number") Integer number) {
        return converter.convert(number);
    }
}
