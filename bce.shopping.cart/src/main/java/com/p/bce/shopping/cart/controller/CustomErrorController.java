package com.p.bce.shopping.cart.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("error", "Page not found (404)");
                model.addAttribute("message", "The page you are looking for does not exist.");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
                String errorMessage = "Internal server error (500)";
                if (exception != null) {
                    errorMessage += ": " + exception.toString();
                }
                model.addAttribute("error", errorMessage);
                model.addAttribute("message", "An error occurred while processing your request.");
            } else {
                model.addAttribute("error", "Error " + statusCode);
                model.addAttribute("message", "An error occurred.");
            }
        } else {
            model.addAttribute("error", "Unknown error");
            model.addAttribute("message", "An unexpected error occurred.");
        }
        
        return "error";
    }
}

