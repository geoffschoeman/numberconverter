package com.gs.numberconverter.controller;

import com.gs.numberconverter.converter.number.NumberToStringConverter;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class NumberConversionControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private NumberConversionController controller;
    
    @MockBean
    private NumberToStringConverter converter;
    
    @PostConstruct
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ExceptionConfiguration())
                .build();
    }
    
    /**
     * Test of main method. Should return success. If the main page template
     * is ever removed this test will fail.
     */
    @Test
    public void testMain() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    /**
     * Test of numberToString method. When a valid integer is passed should return a 200 response.
     */
    @Test
    public void testNumberToString() throws Exception {
         mockMvc.perform(get("/convert/12345")).andExpect(status().isOk());
    }
    
    /**
     * Test of numberToString method. When an invalid value is passed should return a 400 response.
     */
    @Test
    public void testNumberToString_ShouldReturnBadRequestForInvalidValue() throws Exception {
         mockMvc.perform(get("/convert/XXXXX")).andExpect(status().isBadRequest());
    }
    
    /**
     * Test of numberToString method. When the underlying service throws an exception this should return a 500 error.
     */
    @Test
    public void testNumberToString_ShouldReturnInternalServerErrorForServiceFailure() throws Exception {
        when(converter.convert(ArgumentMatchers.anyInt())).thenThrow(RuntimeException.class);
        mockMvc.perform(get("/convert/12345")).andExpect(status().is5xxServerError());
    }
    
    /**
     * Test of numberToString method. Should return a 404 for invalid URL requests.
     */
    @Test
    public void testNumberToString_ShouldReturnNotFoundForInvalidEndpoint() throws Exception {
        mockMvc.perform(get("/endpointthatdoesnotexist")).andExpect(status().isNotFound());
    }
}
