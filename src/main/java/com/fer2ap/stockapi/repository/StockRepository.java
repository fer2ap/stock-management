package com.fer2ap.stockapi.repository;

import java.util.List;
import java.util.Optional;

import com.fer2ap.stockapi.model.Stock;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StockRepository extends CrudRepository<Stock, String> {
    Optional<Stock> findByName(@Param("name") String name);
    void deleteByName(@Param("name") String name);
}


