package com.educacionit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendDistefanoApplicationTest {

    @LocalServerPort
    private int port;

    

    @Test
    void testRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        assertNotNull(restTemplate);
    }

    @Test
    void testPortInfo() {
        assertTrue(port > 0);
    }
}
