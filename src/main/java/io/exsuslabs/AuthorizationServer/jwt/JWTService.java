package io.exsuslabs.AuthorizationServer.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.hash.Hashing;
import io.exsuslabs.AuthorizationServer.domain.UserDomain;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Optional;

public class JWTService {

    private final static String secret = Hashing.sha256().hashString("passwordmagica", StandardCharsets.UTF_8).toString();
    private final static JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).withIssuer("ExsusLabs").build();
    public static String generateJWT(UserDomain userDomain) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withSubject(userDomain.getUsername())
                    .withIssuer("ExsusLabs")
                    .withIssuedAt(Instant.now())
                    .withExpiresAt(Instant.now().plusSeconds(60*5))
                    .withClaim("email", userDomain.getEmail())
                    .sign(algorithm);
        } catch (JWTCreationException e ) {
            return e.getMessage();
        }
    }

    public static Optional<DecodedJWT> validateJWT(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return Optional.of(verifier.verify(token));
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }
    public static String extractUsername(String token) {
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }

}
