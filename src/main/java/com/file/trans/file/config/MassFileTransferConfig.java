package com.file.trans.file.config;

import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class MassFileTransferConfig {

    @Bean
    public RestClient fileDownloadRestClient() {
        // 1. 커넥션 및 읽기 타임아웃 설정 (ConnectionConfig 이용)
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setConnectTimeout(5, TimeUnit.SECONDS) // 연결 타임아웃 (5초)
                .setSocketTimeout(30, TimeUnit.SECONDS) // 읽기 타임아웃 (30초)
                .build();

        // 2. 커넥션 풀 매니저에 타임아웃 및 최대 제한(6개) 적용
        PoolingHttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                .setMaxConnTotal(6) // 전체 시스템 최대 커넥션 6개 제한
                .setMaxConnPerRoute(6) // (선택) 전체 제한에 맞춤
                .setDefaultConnectionConfig(connectionConfig)
                .build();

        // 3. Apache HttpClient 생성
        var httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        // 4. RequestFactory 생성 (팩토리 자체에는 타임아웃 메서드를 호출하지 않음)
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        // 5. RestClient 빌드
        return RestClient.builder()
                .requestFactory(factory)
                .build();
    }

}
