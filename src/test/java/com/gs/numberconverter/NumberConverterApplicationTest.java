package com.gs.numberconverter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class NumberConverterApplicationTest {

    @Autowired
    private ApplicationContext context;

    /**
     * This test verifies all required application properties are loaded. 
     * It will fail otherwise.
     */
    @Test
    void contextLoads() {
        assertNotNull(context);
    }
}
