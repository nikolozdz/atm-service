package com.egs.atmservice.model.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateCardResponse {

    private String cardNumber;

    private String authMethod;
}
