package com.example.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    @Test
    void testGreet() {
        String result = App.greet("DevOps");
        assertEquals("Hello, DevOps!", result);
    }
}
