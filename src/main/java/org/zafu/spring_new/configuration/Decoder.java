package org.zafu.spring_new.configuration;

import java.text.ParseException;
import java.time.Instant;
import java.util.Map;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;


import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Decoder implements JwtDecoder {

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            SignedJWT jwt = SignedJWT.parse(token);
            Instant issueTime = jwt.getJWTClaimsSet().getIssueTime().toInstant();
            Instant expiredTime = jwt.getJWTClaimsSet().getExpirationTime().toInstant();
            Map<String, Object> headers = jwt.getHeader().toJSONObject();
            Map<String, Object> claims = jwt.getJWTClaimsSet().getClaims();
            return new Jwt(token, issueTime, expiredTime, headers, claims);
        } catch (ParseException e) {
            throw new JwtException("Invalid token");
        }

    }
}
