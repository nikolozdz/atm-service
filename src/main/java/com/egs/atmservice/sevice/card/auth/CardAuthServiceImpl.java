package com.egs.atmservice.sevice.card.auth;

import com.egs.atmservice.configuration.cardsession.ValidatedCard;
import com.egs.atmservice.exception.ATMServiceException;
import com.egs.atmservice.model.auth.CardAuthRequest;
import com.egs.atmservice.model.auth.CardAuthResponse;
import com.egs.atmservice.model.auth.ValidateCardResponse;
import com.egs.atmservice.model.card.AuthMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class CardAuthServiceImpl implements CardAuthService{

    private final Logger log = LoggerFactory.getLogger(CardAuthServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ValidatedCard validatedCard;

    @Value("${bankServiceUrl}")
    private String bankServiceURL;

    @Value("${cardAuthApiUrl}")
    private String authCardEndpoint;

    @Value("${closeSessionUrl}")
    private String closeSessionService;

    @Override
    public ValidateCardResponse validateCard(String cardNumber) throws ATMServiceException {
        ResponseEntity<ValidateCardResponse> response = restTemplate.getForEntity(bankServiceURL + authCardEndpoint + "/validateCard?cardNumber=" + cardNumber, ValidateCardResponse.class);
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null){
            String errorMessage = "Validation Failed";
            log.error(errorMessage);
            throw new ATMServiceException(errorMessage);
        }
        validatedCard.setCardNumber(cardNumber);
        validatedCard.setAuthMethod(AuthMethod.valueOf(Objects.requireNonNull(response.getBody()).getAuthMethod()));
        return response.getBody();
    }


    @Override
    public void invalidateSession() {
        validatedCard.setCardNumber(null);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", String.join(";", validatedCard.getCookies()));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        restTemplate.postForObject(bankServiceURL + closeSessionService, entity, Void.class);
    }

    @Override
    public void cardAuthorization(String code) throws ATMServiceException {
        if (validatedCard.getCardNumber() == null) {
            String errorMessage = "Card is Not Validated";
            log.warn(errorMessage);
            throw new ATMServiceException(errorMessage);
        }

        CardAuthRequest cardAuthRequest = new CardAuthRequest();
        cardAuthRequest.setCardNumber(validatedCard.getCardNumber());
        cardAuthRequest.setCode(code);
        HttpEntity<CardAuthRequest> request = new HttpEntity<>(cardAuthRequest);

        ResponseEntity<CardAuthResponse> responseEntity = restTemplate.postForEntity(bankServiceURL + authCardEndpoint + "/auth", request, CardAuthResponse.class);
        CardAuthResponse cardAuthResponse = responseEntity.getBody();
        validatedCard.setAllowedActions(cardAuthResponse != null ? cardAuthResponse.getAllowedActions() : null);
        validatedCard.setCookies(responseEntity.getHeaders().get("Set-Cookie"));

        log.info("Successful authorization");
    }
}
