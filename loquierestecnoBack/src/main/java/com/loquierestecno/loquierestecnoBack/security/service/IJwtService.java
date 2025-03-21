package com.loquierestecno.loquierestecnoBack.security.service;


import org.springframework.security.core.Authentication;

public interface IJwtService {
    String createToken(Authentication authentication);
}
