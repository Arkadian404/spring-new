package org.zafu.spring_new.configuration;

import java.text.ParseException;
import java.util.Objects;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import org.zafu.spring_new.dto.request.IntrospectRequest;
import org.zafu.spring_new.dto.response.IntrospectResponse;
import org.zafu.spring_new.service.AuthenticationService;

import com.nimbusds.jose.JOSEException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Decoder implements JwtDecoder {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    private NimbusJwtDecoder jwtDecoder = null;
    private final AuthenticationService service;

    @Override
    public Jwt decode(String token) throws JwtException {
        IntrospectRequest request = IntrospectRequest.builder().token(token).build();
        try {
            IntrospectResponse response = service.introspect(request);
            if (!response.isValid()) throw new JwtException("Token invalid");
        } catch (JOSEException | ParseException ex) {
            ex.printStackTrace();
        }
        if (Objects.isNull(jwtDecoder)) {
            SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), "HS512");
            jwtDecoder = NimbusJwtDecoder.withSecretKey(keySpec)
                    .macAlgorithm(MacAlgorithm.HS512)
                    .build();
        }
        return jwtDecoder.decode(token);
    }
}
