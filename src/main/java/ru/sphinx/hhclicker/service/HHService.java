package ru.sphinx.hhclicker.service;

import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.springframework.stereotype.Service;
import ru.sphinx.hhclicker.dto.AuthDto;

import java.io.IOException;

/**
 * Copyright (c) 2023 Igor Kartashov SphinxCode
 * All rights reserved.
 */
@Service
@RequiredArgsConstructor
public class HHService {

    private final HttpClientDataExchangeService exchangeService;

    private final String clientId = "UGRGDTLAON8G4LFURIKJCR8HS5K4SUAKHRSQFLKUCG52L4EUJII41S507M4T63F8";
    private final String secret = "JMG50ORJF32R4TL1GAQPHSD1UU2FV5V8M37MI67L0TMAQCJ8KBLMS5MUTFCB81I0";

    private final String baseUrl = "https://api.hh.ru/";

    private final String auth = "https://hh.ru/oauth/token";

    public String auth() throws IOException {
        HttpPost request = new HttpPost(auth);

        AuthDto body = new AuthDto("code", clientId);
        exchangeService.auth(request, body);
        return null;
    }
}
