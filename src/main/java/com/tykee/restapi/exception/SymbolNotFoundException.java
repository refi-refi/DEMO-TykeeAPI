package com.tykee.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SymbolNotFoundException extends RuntimeException {
    public SymbolNotFoundException() {
        super();
    }

    public SymbolNotFoundException(Long symbolId) {
        super(String.format("Symbol with id %d not found", symbolId));
    }

    public SymbolNotFoundException(Long symbolId, Throwable cause) {
        super(String.format("Symbol with id %d not found", symbolId), cause);
    }
}