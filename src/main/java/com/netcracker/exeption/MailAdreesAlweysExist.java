package com.netcracker.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class MailAdreesAlweysExist extends Exception{
    public MailAdreesAlweysExist(String message) {
        super(message);
    }
}
