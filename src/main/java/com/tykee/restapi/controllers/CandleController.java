package com.tykee.restapi.controllers;

import com.tykee.restapi.exceptions.SymbolNotFoundException;
import com.tykee.restapi.models.Candle;
import com.tykee.restapi.repositories.CandleRepository;
import com.tykee.restapi.repositories.SymbolRepository;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CandleController {

    private final CandleRepository candleRepository;
    private final SymbolRepository symbolRepository;

    @Autowired
    public CandleController(CandleRepository candleRepository, SymbolRepository symbolRepository) {
        this.candleRepository = candleRepository;
        this.symbolRepository = symbolRepository;
    }

    @GetMapping("/symbols/{symbolId}/candles")
    public List<Candle> getCandlesBySymbolId(@PathVariable(value = "symbolId") Long symbolId) {
        return candleRepository.findBySymbolId(symbolId);
    }

    @GetMapping("/symbols/{symbolId}/candles/{fromTimestamp}/{toTimestamp}")
    public List<Candle> getCandlesBySymbolIdAndTimestamp(@PathVariable(value = "symbolId") Long symbolId,
                                                         @PathVariable(value = "fromTimestamp") Long fromTimestamp,
                                                         @PathVariable(value = "toTimestamp") Long toTimestamp) {
        return candleRepository.findBySymbolIdAndEndTsBetweenOrderByEndTs(symbolId, fromTimestamp, toTimestamp);
    }

    @PostMapping("/symbols/{symbolId}/candles")
    public Candle createCandle(@PathVariable(value = "symbolId") Long symbolId,
        @Valid @RequestBody Candle candle) {
        return symbolRepository.findById(symbolId).map(symbol -> {
            candle.setSymbol(symbol);
            return candleRepository.save(candle);
        }).orElseThrow(() -> new SymbolNotFoundException(symbolId));
    }

    @PostMapping("/symbols/{symbolId}/candles/batch")
    public List<Candle> createBatch(@PathVariable(value = "symbolId") Long symbolId,
        @Valid @RequestBody List<Candle> candles) {
        return symbolRepository.findById(symbolId).map(symbol -> {
            candles.forEach(candle -> candle.setSymbol(symbol));
            return candleRepository.saveAll(candles);
        }).orElseThrow(() -> new SymbolNotFoundException(symbolId));
    }
}
