/*
 * Copyright (c) 2024. Igor Kartashov SphinxCode
 */

package ru.sphinx.hhclicker.config;

import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.util.TimeValue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.util.concurrent.TimeUnit;

/**
 * Класс конфигурации http клиента. Создается клиент, ему назначается SSL контекст.
 */
@Configuration
@RequiredArgsConstructor
public class HttpClientConfig {
    private final SSLContext sslContext;

    @Bean
    public CloseableHttpClient createApacheHttpClient() {
        final SSLConnectionSocketFactory sslsf =
                new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        final Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("https", sslsf)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();

        PoolingHttpClientConnectionManager poolConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        poolConnectionManager.setMaxTotal(100); // общее количество соединений
        poolConnectionManager.setDefaultMaxPerRoute(20); // максимальное количество соединений на маршрут

        return HttpClients.custom()
                .setConnectionManager(poolConnectionManager)
                .setDefaultRequestConfig(getRequestConfig())
                .build();
    }

    private RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setConnectionKeepAlive(TimeValue.ofSeconds(30))
                .setConnectionRequestTimeout(30, TimeUnit.SECONDS)
                .setResponseTimeout(30, TimeUnit.SECONDS)
                .build();
    }
}
