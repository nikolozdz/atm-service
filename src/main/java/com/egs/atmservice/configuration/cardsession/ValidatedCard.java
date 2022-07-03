package com.egs.atmservice.configuration.cardsession;

import com.egs.atmservice.model.card.AuthMethod;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ValidatedCard {

    private String cardNumber;

    private AuthMethod authMethod;

    private List<String> allowedActions;

    private List<String> cookies = new ArrayList<>();
}
