package com.fer2ap.stockapi.repository;

import com.fer2ap.stockapi.model.Quote;

import org.springframework.data.repository.CrudRepository;

public interface QuoteRepository extends CrudRepository<Quote, Long> {}
