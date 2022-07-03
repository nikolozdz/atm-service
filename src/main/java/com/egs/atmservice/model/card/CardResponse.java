package com.egs.atmservice.model.card;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardResponse {

    private long id;

    private String cardNumber;

    private String pin;
}
