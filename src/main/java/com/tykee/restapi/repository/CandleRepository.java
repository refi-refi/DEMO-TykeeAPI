package com.tykee.restapi.repository;

import com.tykee.restapi.model.Candle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandleRepository extends JpaRepository<Candle, Long> {

    List<Candle> findBySymbolId(Long symbolId);
}
