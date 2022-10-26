package com.tykee.restapi.repositories;

import com.tykee.restapi.models.Candle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandleRepository extends JpaRepository<Candle, Long> {

    List<Candle> findBySymbolId(Long symbolId);

    List<Candle> findBySymbolIdAndEndTsBetweenOrderByEndTs(Long symbolId, Long fromTimestamp, Long toTimestamp);

}
