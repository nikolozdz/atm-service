package com.egs.atmservice.sevice.card.action;

import com.egs.atmservice.exception.ATMServiceException;

import java.math.BigDecimal;

public interface CardActionService {

    BigDecimal checkBalance(String cardNumber) throws ATMServiceException;

    void deposit(String cardNumber, BigDecimal amount) throws ATMServiceException;

    void withdraw(String cardNumber, BigDecimal amount) throws ATMServiceException;
}
