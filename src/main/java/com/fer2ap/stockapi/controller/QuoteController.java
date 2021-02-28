package com.fer2ap.stockapi.controller;

import java.util.ArrayList;
import java.util.List;

import com.fer2ap.stockapi.StockapiApplication;
import com.fer2ap.stockapi.controller.form.QuoteForm;
import com.fer2ap.stockapi.model.Quote;
import com.fer2ap.stockapi.repository.QuoteRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {
    private final QuoteRepository quoteRepository;
    private static final Logger log = LoggerFactory.getLogger(StockapiApplication.class);
    
    public QuoteController (QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @GetMapping("/quote")
    List<Quote> index() {
        List<Quote> response = new ArrayList<Quote>();
        log.info("indexing quotes");
        quoteRepository.findAll().forEach(quote -> response.add(quote));
        return response;
    }

    @PostMapping("/quote")
    Quote insert(@RequestBody QuoteForm quoteForm) {
        Quote quote = new Quote(quoteForm.getValue());
        return quoteRepository.save(quote);
    }
}
