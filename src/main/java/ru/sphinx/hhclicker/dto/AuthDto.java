package ru.sphinx.hhclicker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Copyright (c) 2023 Igor Kartashov SphinxCode
 * All rights reserved.
 */
@Data
@AllArgsConstructor
public class AuthDto {
    @JsonProperty("response_type")
    private String responseType;
    @JsonProperty("client_id")
    private String clientId;
}
