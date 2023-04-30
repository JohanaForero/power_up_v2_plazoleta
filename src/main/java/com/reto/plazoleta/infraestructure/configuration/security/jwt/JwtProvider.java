package com.reto.plazoleta.infraestructure.configuration.security.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.reto.plazoleta.infraestructure.configuration.security.exception.AuthenticationFailedException;
import com.reto.plazoleta.infraestructure.configuration.security.exception.TokenInvalidException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    private final IUserVerifierToken userVerifierToken;

    private static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 2_592_000L;
    private static final String ACCESS_TOKEN_SECRET = "8y/B?E(G+KbPeShVmYq3t6w9z$C&F)J@";

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        userVerifierToken.isValidTokenUser(token);
        try {
            JWT jwt = JWTParser.parse(token);
            JWTClaimsSet claims = jwt.getJWTClaimsSet();
            String email = claims.getSubject();
            List<String> rol = claims.getStringListClaim("rol");
            return new UsernamePasswordAuthenticationToken(email, null,
                    rol.stream().map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList()));
        } catch (ParseException e) {
            throw new AuthenticationFailedException();
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            throw new TokenInvalidException("malformed token");
        } catch (UnsupportedJwtException e) {
            throw new TokenInvalidException("token not supported");
        } catch (ExpiredJwtException e) {
            throw new TokenInvalidException("expired token");
        } catch (IllegalArgumentException e) {
            throw new TokenInvalidException("token empty");
        } catch (SignatureException e) {
            throw new TokenInvalidException("fail in signing");
        }
    }

}
