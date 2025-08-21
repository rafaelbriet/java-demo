package br.com.rafaelbriet.springapidemo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidColorException extends RuntimeException {

    public InvalidColorException(String message) {
        super(message);
    }
}
