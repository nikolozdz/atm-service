package com.egs.atmservice.sevice.card.auth;

import com.egs.atmservice.exception.ATMServiceException;
import com.egs.atmservice.model.auth.ValidateCardResponse;

public interface CardAuthService {

    ValidateCardResponse validateCard(String cardNumber) throws ATMServiceException;

    void invalidateSession();

    void cardAuthorization(String code) throws ATMServiceException;

}
