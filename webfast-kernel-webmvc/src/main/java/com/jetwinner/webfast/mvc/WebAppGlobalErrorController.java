package com.jetwinner.webfast.mvc;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * @author xulixin
 */
//@Configuration
//@Controller
public class WebAppGlobalErrorController implements ErrorController {

    private final static String ERROR_PATH = "/error";

    @Override
    @SuppressWarnings("deprecation")
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH, produces = "text/html")
    public String errorView(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMess = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        request.setAttribute("message", "" + errorMess);
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "/mvc/error404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "/mvc/error500";
            }
        }
        return "/mvc/error";
    }

    @ResponseBody
    @RequestMapping(value = ERROR_PATH)
    public Object errorJson() {
        return "这里放回json数据~";
    }

}
