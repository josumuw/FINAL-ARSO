package pasarela.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(Map<String, String> claims) {
        return Jwts.builder()
        	.setClaims(claims)
        	.setIssuedAt(new Date())
        	.setExpiration(new Date(System.currentTimeMillis() + expiration))
        	.signWith(getSigningKey(), SignatureAlgorithm.HS256)
        	.compact();
    }
    
    private Key getSigningKey() {
        // Construir una clave secreta a partir de la cadena secreta
	    return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
    
    
}
