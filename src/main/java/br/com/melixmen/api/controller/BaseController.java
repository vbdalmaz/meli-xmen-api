package br.com.melixmen.api.controller;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    @SuppressWarnings("unchecked")
    protected <T> ResponseEntity<T> createResponse(T instance) {
        if (instance == null || (instance instanceof Collection && ((Collection<T>) instance).isEmpty())) {
            return new ResponseEntity<T>(NO_CONTENT);
        }
        return new ResponseEntity<T>(instance, OK);
    }
    
    protected <T> ResponseEntity<T> createResponse200() {

        return new ResponseEntity<T>(OK);
    }
    
    protected <T> ResponseEntity<T> createResponse403() {

        return new ResponseEntity<T>(FORBIDDEN);
    }
}
