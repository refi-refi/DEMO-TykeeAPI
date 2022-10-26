package com.tykee.restapi.services;

import com.tykee.restapi.exceptions.SymbolNotFoundException;
import com.tykee.restapi.models.Symbol;
import com.tykee.restapi.repositories.SymbolRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SymbolService {
    private static final Logger logger = LoggerFactory.getLogger(SymbolService.class);
    private final SymbolRepository symbolRepository;

    public SymbolService(SymbolRepository symbolRepository) {
        this.symbolRepository = symbolRepository;
    }

    public List<Symbol> getAllSymbols() {
        return symbolRepository.findAll();
    }

    public Symbol getSymbolById(Long symbolId) {
        logger.trace("getSymbolById: {}", symbolId);
        return symbolRepository.findBySymbolId(symbolId)
            .orElseThrow(() -> new SymbolNotFoundException(symbolId));
    }

    public void deleteSymbolById(Long symbolId) {
        logger.trace("Deleting symbol with id: {}", symbolId);
        symbolRepository.deleteBySymbolId(symbolId);
    }
}
