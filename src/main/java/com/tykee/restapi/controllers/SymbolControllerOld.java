package com.tykee.restapi.controllers;


import com.tykee.restapi.exceptions.SymbolNotFoundException;
import com.tykee.restapi.models.Symbol;
import com.tykee.restapi.repositories.SymbolRepository;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//@RestController
public class SymbolControllerOld {
    private final SymbolRepository symbolRepository;

    @Autowired
    public SymbolControllerOld(SymbolRepository repository) {
        this.symbolRepository = repository;
    }

    @GetMapping("/symbols")
    public List<Symbol> getAllSymbols() {
        return symbolRepository.findAll();
    }

    @GetMapping("/symbols/{symbolId}")
    public Symbol getSymbolById(@PathVariable Long symbolId) {
        return symbolRepository.findBySymbolId(symbolId)
            .orElseThrow(() -> new SymbolNotFoundException(symbolId));
    }

    @GetMapping("/symbols/ticker/{symbolName}")
    public Symbol getSymbolByName(@PathVariable String symbolName) {
        return symbolRepository.findByName(symbolName)
            .orElseThrow(() -> new SymbolNotFoundException(symbolName));
    }

    @PostMapping("/symbols")
    public ResponseEntity<Void> createSymbol(@Valid @RequestBody Symbol symbol) {
        symbolRepository.save(symbol);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(symbol.getSymbolId())
            .toUri();
        return ResponseEntity.created(location).build();
    }
//    @PostMapping("/symbols")
//    public Symbol createPost(@RequestBody @Valid Symbol symbol) {
//        return symbolRepository.save(symbol);
//    }

    @PostMapping("/symbols/batch")
    public List<Symbol> createBatch(@RequestBody @Valid List<Symbol> symbols) {
        return symbolRepository.saveAll(symbols);
    }

    @PutMapping("/symbols")
    public Symbol updateSymbol(@RequestBody @Valid Symbol symbol) {
        if(symbol == null || symbol.getSymbolId() == null) {
            throw new IllegalArgumentException("Symbol or ID must not be null!");
        }
        Optional<Symbol> optionalSymbol = symbolRepository.findBySymbolId(symbol.getSymbolId());
        if(optionalSymbol.isEmpty()) {
            throw new SymbolNotFoundException(symbol.getSymbolId());
        }

        Symbol existingSymbol = optionalSymbol.get();
        existingSymbol.setSymbolId(symbol.getSymbolId());
        existingSymbol.setName(symbol.getName());
        existingSymbol.setDigits(symbol.getDigits());

        return symbolRepository.save(existingSymbol);
    }

    @DeleteMapping("/symbols/{symbolId}")
    public void deleteSymbol(@PathVariable Long symbolId) throws SymbolNotFoundException {
        if(symbolRepository.findBySymbolId(symbolId).isEmpty()) {
            throw new SymbolNotFoundException(symbolId);
        }
        symbolRepository.deleteBySymbolId(symbolId);
    }

}
