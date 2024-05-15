package com.educacionit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/fecha")
public class FechaController {

    private static final String API_URL = "http://worldtimeapi.org/api/ip";

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> obtenerFechaYHora() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try {
            String resultado = restTemplate.getForObject(API_URL, String.class);
            response = new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (Exception e) {
            response = new ResponseEntity<>("Error al obtener la fecha y hora", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
