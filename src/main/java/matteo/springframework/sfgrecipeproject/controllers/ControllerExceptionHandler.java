package matteo.springframework.sfgrecipeproject.controllers;

import lombok.extern.slf4j.Slf4j;
import matteo.springframework.sfgrecipeproject.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNoValidValue(Exception nfe) {
        log.error("Handling No Valid Value Exception");
        log.error(nfe.getMessage());
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/recipe/400error");
        modelAndView.addObject("nfe", nfe);

        return modelAndView;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception) {
        log.error("Handling not found exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("/recipe/404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }
}
