package com.egs.atmservice.sevice.card.action;

import com.egs.atmservice.configuration.cardsession.ValidatedCard;
import com.egs.atmservice.exception.ATMServiceException;
import com.egs.atmservice.model.card.DepositRequest;
import com.egs.atmservice.model.card.WithdrawRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
public class CardActionServiceImpl implements CardActionService {

    private final Logger log = LoggerFactory.getLogger(CardActionServiceImpl.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ValidatedCard validatedCard;

    @Value("${bankServiceUrl}")
    private String bankServiceURL;

    @Value("${cardActionApiUrl}")
    private String actionsEndpoint;

    private final static String httpErrorMessage = "Error while getting response from BankService";

    @Override
    public BigDecimal checkBalance(String cardNumber) throws ATMServiceException {
        validateAction("CHECK_BALANCE");
        HttpEntity<String> request = new HttpEntity<>(getHttpHeaders());

        ResponseEntity<BigDecimal> response = restTemplate.exchange(bankServiceURL + actionsEndpoint + "/checkBalance?cardNumber=" + cardNumber, HttpMethod.GET, request, BigDecimal.class);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error(httpErrorMessage);
            throw new ATMServiceException(httpErrorMessage);
        }
        return response.getBody();
    }

    @Override
    public void deposit(String cardNumber, BigDecimal amount) throws ATMServiceException {
        validateAction("DEPOSIT");
        DepositRequest depositRequest = new DepositRequest();
        depositRequest.setAmount(amount);
        depositRequest.setCardNumber(validatedCard.getCardNumber());

        HttpEntity<DepositRequest> request = new HttpEntity<>(depositRequest, getHttpHeaders());

        String message = restTemplate.postForObject(bankServiceURL + actionsEndpoint + "/deposit", request, String.class);
        log.info("Deposit result: " + message);
    }

    @Override
    public void withdraw(String cardNumber, BigDecimal amount) throws ATMServiceException {
        validateAction("WITHDRAW");
        WithdrawRequest withdrawalRequest = new WithdrawRequest();
        withdrawalRequest.setAmount(amount);
        withdrawalRequest.setCardNumber(validatedCard.getCardNumber());

        HttpEntity<WithdrawRequest> httpEntity = new HttpEntity<>(withdrawalRequest, getHttpHeaders());

        String message = restTemplate.postForObject(bankServiceURL + actionsEndpoint + "/withdraw", httpEntity, String.class);
        log.info("Withdrawal result: " + message);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cookie", String.join(";", validatedCard.getCookies()));
        return headers;
    }

    private void validateAction(String action) throws ATMServiceException {
        if (!validatedCard.getAllowedActions().contains(action)) {
            String errorMessage = action + " action NOT allowed";
            log.warn(errorMessage);
            throw new ATMServiceException(errorMessage);
        }
    }
}
