package com.jetwinner.webfast.mvc;

import com.jetwinner.webfast.kernel.exception.RuntimeGoingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class WebAppGlobalExceptionHandler {

    @ExceptionHandler(value = {RuntimeGoingException.class})
    public ModelAndView runtimeGoingExceptionHandler(Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", e.toString());
        mav.setViewName("/error/500");
        return mav;
    }
}
