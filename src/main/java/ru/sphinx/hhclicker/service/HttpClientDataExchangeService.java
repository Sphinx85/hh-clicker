package ru.sphinx.hhclicker.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Service;
import ru.sphinx.hhclicker.config.ResponseHandler;

import java.io.IOException;
import java.util.Arrays;

/**
 * Сервис, представляющий собой сконфигурированный http клиент.
 * К сервису подключены дополнительные вспомогательные классы.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public final class HttpClientDataExchangeService {
    public static final String AUTHORIZATION_HEADER = "Bearer";
    private final ObjectMapper mapper;
    private final CloseableHttpClient httpClient;
    private final ResponseHandler responseHandler;

    public void postMethod(HttpPost request, String token, Object body) throws IOException {
        initHeaders(request, token);
        request.setEntity(new StringEntity(
                mapper.writeValueAsString(body), ContentType.APPLICATION_JSON
        ));

        log.info(request.toString());
        log.info(Arrays.toString(request.getHeaders()));
        log.info(mapper.writeValueAsString(body));

        try (ClassicHttpResponse response = httpClient.execute(request, responseHandler)) {
            log.info(response.toString());
        }
    }

    public void getMethod(HttpPost request, String token, Object body) throws IOException {
        initHeaders(request, token);

        log.info(request.toString());
        log.info(Arrays.toString(request.getHeaders()));
        log.info(mapper.writeValueAsString(body));

        try (ClassicHttpResponse response = httpClient.execute(request, responseHandler)) {
            log.info(response.toString());
        }
    }

    public void auth(HttpPost request, Object body) throws IOException {
        request.setEntity(new StringEntity(mapper.writeValueAsString(body)));
        try (ClassicHttpResponse response = httpClient.execute(request, responseHandler)) {
            log.info(response.toString());
        }

    }


    private void initHeaders(HttpRequest request, String token) {
        if (token != null) {
            request.setHeader(AUTHORIZATION_HEADER, token);
        }
        request.setHeader("Accept", "*/*");
        request.setHeader("Content-Type", "application/json");
    }

}
