package com.loquierestecno.loquierestecnoBack.security.service.impl;


import org.springframework.stereotype.Service;
import com.loquierestecno.loquierestecnoBack.constants.ConstantesGenerales;
import com.loquierestecno.loquierestecnoBack.security.service.IJwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

@Service
public class JwtService implements IJwtService {


    private static final String AUTHORITIES_KEY = "scp";

    private final Key key;

    public JwtService() {
        byte[] keyBytes;
        String secret = ConstantesGenerales.LLAVE_CIFRADO;
        keyBytes = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;

        validity = new Date(now + ConstantesGenerales.TIEMPO_VALIDEZ_TOKEN_MILIS);

        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .compact();
    }

}
