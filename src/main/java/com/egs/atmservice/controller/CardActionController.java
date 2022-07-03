package com.egs.atmservice.controller;

import com.egs.atmservice.exception.ATMServiceException;
import com.egs.atmservice.model.card.DepositRequest;
import com.egs.atmservice.model.card.WithdrawRequest;
import com.egs.atmservice.sevice.card.action.CardActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@RestController
@RequestMapping("api/cards/action")
public class CardActionController {

    @Autowired
    private CardActionService cardActionService;

    @GetMapping("checkBalance")
    public BigDecimal checkBalance(@RequestParam(value = "cardNumber") String cardNumber) throws ATMServiceException {
        return cardActionService.checkBalance(cardNumber);
    }

    @PostMapping("deposit")
    public void deposit(@RequestBody DepositRequest depositRequest) throws ATMServiceException {
        cardActionService.deposit(depositRequest.getCardNumber(), depositRequest.getAmount());
    }

    @PostMapping("withdraw")
    public void withdrawal(HttpSession httpSession, @RequestBody WithdrawRequest withdrawalRequest) throws ATMServiceException {
        cardActionService.withdraw(withdrawalRequest.getCardNumber(), withdrawalRequest.getAmount());
    }
}
