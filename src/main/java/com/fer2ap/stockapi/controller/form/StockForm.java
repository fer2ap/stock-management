package com.fer2ap.stockapi.controller.form;

import java.util.List;

import com.fer2ap.stockapi.model.Quote;

import org.springframework.lang.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockForm {
    String name;
    @Nullable
    List<Float> quotes;
}