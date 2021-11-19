package com.example.demo.client;


import com.example.demo.domain.Wso2UserRequest;
import com.example.demo.domain.Wso2UserResponse;
import feign.Headers;
import feign.RequestLine;

public interface IdentityServiceScimClient {

    @RequestLine("POST /scim2/Users")
    @Headers("Content-Type: application/json")
    Wso2UserResponse createUser(Wso2UserRequest request);
}
