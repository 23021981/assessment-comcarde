package com.comcarde.assessment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Rule not found")
public class RuleNotFoundException extends Exception {

    public RuleNotFoundException(String message){
        super(message);
    }
}
