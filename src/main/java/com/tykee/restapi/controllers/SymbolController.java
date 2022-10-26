package com.tykee.restapi.controllers;

import com.tykee.restapi.models.Symbol;
import com.tykee.restapi.services.SymbolService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SymbolController {
    private final SymbolService symbolService;

    public SymbolController(SymbolService symbolService) {
        this.symbolService = symbolService;
    }

    @GetMapping("/symbols")
    public List<Symbol> getAllSymbols() {
        return symbolService.getAllSymbols();
    }

    @GetMapping("/symbols/{symbolId}")
    public Symbol getSymbolById(@PathVariable Long symbolId) {
        return symbolService.getSymbolById(symbolId);
    }

    @DeleteMapping("/symbols/{symbolId}")
    public void deleteSymbolById(@PathVariable Long symbolId) {
        symbolService.deleteSymbolById(symbolId);
    }
}
