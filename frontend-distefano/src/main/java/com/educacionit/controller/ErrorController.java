package com.educacionit.controller;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(Model model) {
    	model.addAttribute("pageTitle", "Error 404 - Página no encontrada");
    	model.addAttribute("imagePath", "/img/spring.png");
    	model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
        model.addAttribute("imageError", "/img/error-404.jpg");       
        model.addAttribute("titulo", "Página inexistente");
        return "error404";
    }
}