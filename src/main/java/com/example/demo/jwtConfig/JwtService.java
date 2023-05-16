package com.example.demo.jwtConfig;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	@Value("${security.jwt.expiracao}")
	private String expiracao;
	
	@Value("${security.jwt.chave-assinatura}")
	private String chaveAssinatura;
	
	private SecretKey getChaveAssinatura() {
		return Keys.hmacShaKeyFor(chaveAssinatura.getBytes(StandardCharsets.UTF_8));
	}
	
	public String gerarToken(Usuario usuario) {
		long expString = Long.valueOf(expiracao);
		LocalDateTime dataHoraExp = LocalDateTime.now().plusMinutes(expString);
		Instant instant = dataHoraExp.atZone(ZoneId.systemDefault()).toInstant();
		Date data = Date.from(instant);
		
		HashMap<String, Object> claims = new HashMap<>();
		claims.put("idUsuario", usuario.getId());
		claims.put("roles", usuario.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"});
		
		String token = Jwts
							.builder()
							.setClaims(claims)
							.setSubject(usuario.getLogin())
							.setIssuer("localhost:8080")
							.setIssuedAt(new Date())
							.setExpiration(data)
							.signWith(getChaveAssinatura(), SignatureAlgorithm.HS512).compact();
		
		return token;
		
	}
	
	private Claims obterClaims(String token) throws ExpiredJwtException {
		
		return Jwts
					.parserBuilder()
					.setSigningKey(getChaveAssinatura())
					.build()
					.parseClaimsJws(token)
					.getBody();
		
	}
	
	
	public boolean tokenValido(String token) {
		
		try {
			Claims claims = obterClaims(token);
			Date dataExpiracao = claims.getExpiration(); 
			LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			return !LocalDateTime.now().isAfter(data);
			
		}catch(Exception e) {
			return false;
		}
		
	}
	
	public String obterLoginUsuario(String token) throws ExpiredJwtException {
		return (String) obterClaims(token).getSubject();
		
	}

}
