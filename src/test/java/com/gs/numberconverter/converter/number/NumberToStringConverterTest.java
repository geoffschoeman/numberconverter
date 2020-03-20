package com.gs.numberconverter.converter.number;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class NumberToStringConverterTest {
    
    @Autowired
    private NumberToStringConverter converter;

    /**
     * Test of convert when given 0 should return 'Zero'.
     */
    @Test
    public void testConvert_Zero() {
        String expected = "Zero";
        
        String result = converter.convert(0);

        assertEquals(expected, result);
    }
    
    /**
     * Test of convert when given Integer.MAX_VALUE will return the appropriate string.
     */
    @Test
    public void testConvert_MaxInt() {
        String expected = "Two billion one hundred and forty seven million four hundred and eighty three thousand six hundred and forty seven";
        
        String result = converter.convert(Integer.MAX_VALUE);
        
        assertEquals(expected, result);
    }
    
    /**
     * Test of convert when given Integer.MIN_VALUE will return the appropriate string.
     */
    @Test
    public void testConvert_MinInt() {
        String expected = "Negative two billion one hundred and forty seven million four hundred and eighty three thousand six hundred and forty eight";
        
        String result = converter.convert(Integer.MIN_VALUE);

        assertEquals(expected, result);
    }
    
    /**
     * Test of convert when when a negative number is given.
     */
    @Test
    public void testConvert_NegativeNumbersShouldHaveTheNegativePrefix() {
        String expected = "Negative";
        
        String result = converter.convert(-1);

        assert(result.startsWith(expected));
    }
    
    /**
     * Test of convert when given null should throw an IllegalArgumentException.
     */
    @Test
    public void testConvert_ShouldThrowAnExceptionIfNullIsPassed() {
        
        assertThrows(IllegalArgumentException.class, () -> {
            converter.convert(null);
        });
    }
    
    /**
     * Test the expected input/output for the samples included in the project requirements.
     */
    @Test
    public void testConvert_SampleInputMatchesExpectedResults() {
        assertEquals("Zero", converter.convert(0));
        assertEquals("Thirteen", converter.convert(13));
        assertEquals("Eighty five", converter.convert(85));
        assertEquals("Five thousand two hundred and thirty seven", converter.convert(5237));
    }
}
