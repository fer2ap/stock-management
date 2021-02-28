package com.fer2ap.stockapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.websocket.server.PathParam;

import com.fer2ap.stockapi.StockapiApplication;
import com.fer2ap.stockapi.controller.form.QuoteForm;
import com.fer2ap.stockapi.controller.form.StockForm;
import com.fer2ap.stockapi.controller.form.UpdateStockForm;
import com.fer2ap.stockapi.model.Quote;
import com.fer2ap.stockapi.model.Stock;
import com.fer2ap.stockapi.repository.QuoteRepository;
import com.fer2ap.stockapi.repository.StockRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {
    private final StockRepository stockRepository;
    private final QuoteRepository quoteRepository;
    private static final Logger log = LoggerFactory.getLogger(StockapiApplication.class);

    public StockController (StockRepository stockRepository, QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
        this.stockRepository = stockRepository;
    }

    private Map<String, Object> mapearStock (Stock stock) {
        Map<String, Object> mappedStock = new HashMap<>();
        mappedStock.put("name", stock.getName());
        mappedStock.put("quotes", stock.getQuoteValues());
        return mappedStock;
    }

    @GetMapping("/stock")
    List<Map<String, Object>> listStock (@Param("name") String name) {
        List<Map<String, Object>> response = new ArrayList<>();
        if (name == null) {
            stockRepository.findAll().forEach(stock -> {
                response.add(mapearStock(stock));
            });
        } else {
            Optional<Stock> stock = stockRepository.findByName(name);
            if (stock.isEmpty()) {
                log.info(String.format("No stock found with this name: %s", name));
                return null;
            }
            if (stock.isPresent()) {
                response.add(mapearStock(stock.get()));
            }
        }
        return response;
    }

    @PostMapping("/stock")
    Map<String, Object> insertStock (@RequestBody StockForm stockForm) {
        if (stockRepository.findByName(stockForm.getName()).isPresent()){
            log.info(String.format("Stock already exists: %s", stockForm.getName()));
            return null;
        }
        Map<String, Object> response = new HashMap<String, Object>();
        Stock stock = new Stock(stockForm.getName());
        if (stockForm.getQuotes() != null) {
            stockForm.getQuotes()
                .forEach(value -> {
                    Quote quote = new Quote(value);
                    quote.setStock(stock);
                    quoteRepository.save(quote);
                });
        }
        Stock savedStock = stockRepository.save(stock);
        response.put("name", savedStock.getName());
        response.put("quotes", stockForm.getQuotes());
        return response;
    }
    
    @PatchMapping("/stock/{name}")
    Map<String, Object> updateStock(@RequestBody UpdateStockForm updateStockForm, @PathVariable("name") String name){
        log.info(String.format("%s, %s", name, updateStockForm.getQuotes().toString()));
        Optional<Stock> stock = stockRepository.findByName(name);
        if (stock.isEmpty()) {
            log.info(String.format("No stock found with this name: %s", name));
            return null;
        }
        if (updateStockForm.getQuotes().isEmpty()) {
            log.info(String.format("No quotes were given: %s", updateStockForm.getQuotes().toString()));
            return null;
        }
        updateStockForm.getQuotes().forEach(value -> {
            Quote quote = new Quote(value);
            quote.setStock(stock.get());
            quoteRepository.save(quote);
        });
        return mapearStock(stock.get());
    }

    @DeleteMapping("/stock/{name}")
    void deleteStock (@PathVariable("name") String name) {
        Optional<Stock> stock = stockRepository.findByName(name);  
        stockRepository.delete(stock.get());
        log.info(String.format("Stock %s deleted", name));
    }
 }
