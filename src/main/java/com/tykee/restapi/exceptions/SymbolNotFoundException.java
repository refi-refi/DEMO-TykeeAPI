package com.tykee.restapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SymbolNotFoundException extends RuntimeException {
    public SymbolNotFoundException(Long symbolId) {
        super(String.format("Symbol with id %d not found", symbolId));
    }

    public SymbolNotFoundException(String symbolName) {
        super(String.format("Symbol with name %s not found", symbolName));
    }
}