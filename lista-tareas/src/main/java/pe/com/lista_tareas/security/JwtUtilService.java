package pe.com.lista_tareas.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtUtilService {
    // WEB_DEV_UPC_202301 => [Base64] => V0VCX0RFVl9VUENfMjAyMzAx
    private static final String JWT_SIGNATURE_KEY = "V0VCX0RFVl9VUENfMjAyMzAx";
    private static Long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * (long)3; // 3 horas de validez del token


    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_SIGNATURE_KEY).parseClaimsJws(token).getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsFunction) {
        return claimsFunction.apply(extractAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


    public boolean validateToken(String token, SecurityUser user) {
        String username = extractUsername(token);
        return (!isTokenExpired(token)) && (username.equals(user.getUsername()));
    }

    public String createToken(String subject, Map<String, Object> claims) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS256, JWT_SIGNATURE_KEY)
                .compact();
    }

    public String generateToken(SecurityUser securityUser) {
        Map<String, Object> claims=new HashMap<>();
        Object roles = securityUser.getAuthorities().stream().collect(Collectors.toList()).get(0);
        claims.put("rol",roles);
        return createToken(securityUser.getUsername(), claims);
    }
}
