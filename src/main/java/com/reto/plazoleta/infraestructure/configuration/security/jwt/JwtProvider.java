package com.reto.plazoleta.infraestructure.configuration.security.jwt;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import com.reto.plazoleta.infraestructure.configuration.security.exception.AuthenticationFailedException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.DecodingException;
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
    private static final String ACCESS_TOKEN_SECRET = "8y/B?E(G+KbPeShVmYq3t6w9z$C&F)J@";

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
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

    public Boolean validateToken(String tokenNoBearerPrefix) {
        try {
            Jwts.parserBuilder().setSigningKey(ACCESS_TOKEN_SECRET.getBytes()).build().parseClaimsJws(tokenNoBearerPrefix);
            if(!userVerifierToken.isValidTokenUser("Bearer " + tokenNoBearerPrefix)) {
                return false;
            }
            return true;
        } catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException |
                SignatureException | DecodingException e) {
            return false;
        }
    }

}
