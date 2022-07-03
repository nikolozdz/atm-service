package com.egs.atmservice.model.auth;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CardAuthResponse {

    private String cardNumber;

    private List<String> allowedActions = new ArrayList<>();
}

