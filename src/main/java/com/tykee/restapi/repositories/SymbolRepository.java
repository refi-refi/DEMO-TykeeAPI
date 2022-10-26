package com.tykee.restapi.repositories;


import com.tykee.restapi.models.Symbol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface SymbolRepository extends JpaRepository<Symbol, Long> {
    Optional<Symbol> findBySymbolId(Long symbolId);
    Optional<Symbol> findByName(String name);
    void deleteBySymbolId(Long symbolId);
}
