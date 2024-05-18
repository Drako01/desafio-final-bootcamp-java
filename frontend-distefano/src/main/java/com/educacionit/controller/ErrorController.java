package com.educacionit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(HttpServletRequest request, Model model, HttpSession session) {
        String lastVisitedUrl = (String) session.getAttribute("lastVisitedUrl");
        if (lastVisitedUrl == null) {
            lastVisitedUrl = "/";
        } else if (lastVisitedUrl.contains("/productos/detalles/")) {
            lastVisitedUrl = "/productos/";
			model.addAttribute("titulo", "Producto inexistente");
        	model.addAttribute("mensaje", "Lo siento, el Producto que estás buscando no se encuentra.");
        } else {        	
        	model.addAttribute("titulo", "Página inexistente");
        	model.addAttribute("mensaje", "Lo siento, la página que estás buscando no se encuentra.");
        }

        model.addAttribute("lastVisitedUrl", lastVisitedUrl);
        model.addAttribute("pageTitle", "Error 404 - Página no encontrada");
        model.addAttribute("imagePath", "/img/spring.png");
        model.addAttribute("imagePathEducaciontIt", "/img/educacionit.svg");
        model.addAttribute("imageError", "/img/error-404.jpg");
        return "error404";
    }
}
