/*
 * Copyright (c) 2024. Igor Kartashov SphinxCode
 */

package ru.sphinx.hhclicker.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

/**
 * Класс конфигурации SSL контекста. Служит так же для поиска ЭЦП на носителях
 * и загрузки их в контекст.
 */
@Configuration
@Slf4j
public class SSLContextConfig {


    @Bean
    public SSLContext sslContext()
            throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException, KeyManagementException {


        // Путь к стандартному trustStore
        String trustStorePath = System.getProperty("java.home") + "/lib/security/cacerts";
        String trustStorePassword = "changeit"; // Стандартный пароль

        // Загрузка trustStore
        KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
        try (FileInputStream trustStoreStream = new FileInputStream(trustStorePath)) {
            trustStore.load(trustStoreStream, trustStorePassword.toCharArray());
        }

        // Инициализация TrustManagerFactory
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

        // Создание SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagers, null);

        return sslContext;
    }
}
