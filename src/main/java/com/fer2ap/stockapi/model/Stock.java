package com.fer2ap.stockapi.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Stock {
    
    @Id
    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    @OneToMany(mappedBy = "stock", 
            orphanRemoval = true, 
            fetch = FetchType.LAZY,
            cascade=CascadeType.ALL)
    private List<Quote> quotes;

    protected Stock(){}

    public Stock(String name) {
        this.name = name;
    }

    @Transient
    public List<Float> getQuoteValues () {
        List<Quote> quotes = this.getQuotes();
        if (quotes == null) return new ArrayList<>();
        return quotes
            .stream()
            .map(quote -> {
                return quote.getValue();
            })
            .collect(Collectors.toList());
    }
}