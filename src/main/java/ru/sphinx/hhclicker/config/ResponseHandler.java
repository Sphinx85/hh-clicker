package ru.sphinx.hhclicker.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Copyright (c) 2023 Igor Kartashov SphinxCode
 * All rights reserved.
 */
@Service
@Slf4j
public class ResponseHandler implements HttpClientResponseHandler<ClassicHttpResponse> {
    @Override
    public ClassicHttpResponse handleResponse(ClassicHttpResponse response) throws HttpException, IOException {
        if (response.getCode() >= 200 && response.getCode() < 300) {
            String responseBody = EntityUtils.toString(response.getEntity());
            log.info("Response Body: " + responseBody);
        } else {
            log.error("Error Response Code: " + response.getCode());
            String errorBody = EntityUtils.toString(response.getEntity());
            log.error("Error Response Body: " + errorBody);
        }
        return response;
    }
}
