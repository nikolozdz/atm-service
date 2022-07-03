package com.egs.atmservice.controller;

import com.egs.atmservice.exception.ATMServiceException;
import com.egs.atmservice.model.auth.ValidateCardResponse;
import com.egs.atmservice.sevice.card.auth.CardAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("api/cards/auth")
public class CardAuthSessionController {

    @Autowired
    CardAuthService cardAuthService;

    @GetMapping("validate")
    public ValidateCardResponse validateCard(@RequestParam(value = "cardNumber") String cardNumber) throws ATMServiceException {
        return cardAuthService.validateCard(cardNumber);
    }

    @PostMapping("cardAuthorization")
    public void cardAuthorization(@RequestParam(value = "code") String code) throws ATMServiceException {
        cardAuthService.cardAuthorization(code);
    }

    @PostMapping("invalidateSession")
    public void invalidateSession() {
        cardAuthService.invalidateSession();
    }
}
