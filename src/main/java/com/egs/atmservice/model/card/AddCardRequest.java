package com.egs.atmservice.model.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCardRequest {

    @JsonProperty(required = true)
    private String personalId;

    @JsonProperty(required = true)
    private String authMethod;

    @JsonProperty(required = true)
    private String fingerprint;
}
