package com.example.demo.config;

import com.example.demo.client.IdentityServiceScimClient;
import feign.Feign;
import feign.Request;
import feign.auth.BasicAuthRequestInterceptor;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignConfiguration {

    @Bean
    IdentityServiceScimClient identityServiceScimClient() throws URISyntaxException, MalformedURLException {

        URL url = new URL("https://localhost:9443");
        String scimUrl = url.toURI().toString();

        return Feign.builder().client(new ApacheHttpClient()).encoder(new JacksonEncoder()).decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(IdentityServiceScimClient.class))
                .options(new Request.Options(120000, 120000))
                .requestInterceptor(new BasicAuthRequestInterceptor("admin", "admin"))
                .target(IdentityServiceScimClient.class, scimUrl);

    }
}
