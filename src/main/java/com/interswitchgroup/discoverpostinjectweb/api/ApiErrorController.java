package com.interswitchgroup.discoverpostinjectweb.api;

import com.interswitchgroup.discoverpostinjectweb.exception.ApplicationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class ApiErrorController extends AbstractErrorController {

    private String errorPath;

    public ApiErrorController(@Value("${error.path:/error}") String errorPath,
                              ErrorAttributes errorAttributes) {
        super(errorAttributes, null);
        this.errorPath = errorPath;
    }

    @Override
    public String getErrorPath() {
        return this.errorPath;
    }

    @RequestMapping(value = "${error.path:/error}")
    public void error(HttpServletRequest request) throws Throwable {
        Map<String, Object> attributes = getErrorAttributes(request, false);
        Object exception = attributes.get("throwable");
        if(exception != null) {
            throw (Throwable) exception;
        } else {
            throw new ApplicationException(String.format("%s - %s",
                    attributes.get("error"), attributes.get("message")));
        }
    }

}
