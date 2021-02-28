package com.fer2ap.stockapi.controller.form;

import java.util.List;

import com.fer2ap.stockapi.model.Quote;

import lombok.Getter;
import lombok.Setter;

public class UpdateStockForm {
    @Getter
    @Setter
    private List<Float> quotes;
}
