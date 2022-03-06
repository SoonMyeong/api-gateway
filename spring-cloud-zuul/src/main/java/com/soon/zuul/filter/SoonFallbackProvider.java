package com.soon.zuul.filter;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

//https://cloud.spring.io/spring-cloud-netflix/multi/multi__router_and_filter_zuul.html
@Component
public class SoonFallbackProvider implements FallbackProvider {

    // Zuul 라이브러리 안에 Hystrix 포함되어 있으므로 Hystrix 쓰면 됨

    @Override
    public String getRoute() {
        return null;
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        String message = null;
        if (cause instanceof HystrixTimeoutException) {
            message = "HystrixTimeout";
            return response(HttpStatus.GATEWAY_TIMEOUT, message);
        } else {
            message = "service not woring";
            return response(HttpStatus.INTERNAL_SERVER_ERROR, message);
        }
    }

    private ClientHttpResponse response(final HttpStatus status, String message) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
                String body = "{\"error\":\""+message+"\"}";

                return new ByteArrayInputStream(body.getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }

}
