package com.tykee.restapi.controller;


import com.tykee.restapi.exception.SymbolNotFoundException;
import com.tykee.restapi.model.Symbol;
import com.tykee.restapi.repository.SymbolRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SymbolController {

    private final SymbolRepository symbolRepository;

    @Autowired
    public SymbolController(SymbolRepository repository) {
        this.symbolRepository = repository;
    }

    @GetMapping("/symbols")
    public List<Symbol> getAllSymbols() {
        return symbolRepository.findAll();
    }

    @GetMapping("/symbols/{symbolId}")
    public Symbol getSymbolById(@PathVariable Long symbolId) {
        return symbolRepository.findById(symbolId)
            .orElseThrow(() -> new SymbolNotFoundException(symbolId));
    }

    @PostMapping("/symbols")
    public Symbol createPost(@Valid @RequestBody Symbol symbol) {
        return symbolRepository.save(symbol);
    }

}
