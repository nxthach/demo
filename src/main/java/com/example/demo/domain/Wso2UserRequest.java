package com.example.demo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Wso2UserRequest {
    private String userName;
    private String password;
}
