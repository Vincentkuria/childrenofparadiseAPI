package com.childrenofparadiseorphanage.api.config.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil(
    private val env: Environment
) {
    val secret=env.getProperty("jwt.secret")!!

    fun generateToken(subject: String): String? {
        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis()+1000*60*60*24))//1 day expiration
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray()),SignatureAlgorithm.HS512)
            .compact()
    }

    fun getClaims(token: String):Claims{
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(secret.toByteArray()))
            .build()
            .parseClaimsJws(token).body
    }

    fun isTokenValid(token:String):Boolean{
        return !getClaims(token).expiration.before(Date())
    }
}