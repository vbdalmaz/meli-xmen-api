package br.com.melixmen.api.handler;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import br.com.melixmen.api.exception.DNAInvalidException;
import br.com.melixmen.api.utils.MeliXmenStringConfig;


@ControllerAdvice
public class DefaultExceptionHandler extends DefaultHandlerExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);
    
    @RequestMapping(produces = { APPLICATION_JSON_UTF8_VALUE })
    @ResponseStatus(value = BAD_REQUEST)
    @ExceptionHandler({DNAInvalidException.class })
    public @ResponseBody ResponseError handleNotADNAStructureValid(DNAInvalidException ex) {
        logger.error("Error DNA is not Valid ", ex);

        return new ResponseError(HttpStatus.BAD_REQUEST.value(), MeliXmenStringConfig.DNA_ERROR_EXCEPTION_MESSAGE);
    }
}