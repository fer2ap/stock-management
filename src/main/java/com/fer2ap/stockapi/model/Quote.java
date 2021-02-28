package com.fer2ap.stockapi.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Quote {
    @SequenceGenerator(
        name = "quote_sequence",
        sequenceName = "quote_sequence",
        allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "quote_sequence"
    )
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private Float value;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="stock_id")
    // @Getter
    @Setter
    private Stock stock;

    protected Quote () {}

    public Quote(Float value) {
        this.value = value;
    }
}