package com.generation.blogpessoal.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Component
//Classe que pode injetar e instanciar qualquer dependencia mencionada nessa classe

/**
 * @Component: é uma anotação genérica para qualquer componente gerenciado pelo
 *             Spring. O Spring fornece algumas anotações especializadas,
 *             como: @Controller e @Service. Todas elas fornecem a mesma função
 *             que a anotação @Component. Todas elas agem da mesma forma, porque
 *             são anotações compostas internamente pela anotação @Component.
 *             Elas são como "alias" (apelido) para a anotação @Component,
 *             entretanto cada uma possui usos especializados e específicos.
 * 
 *             O grande diferencial desta anotação é que ela é detectada
 *             automaticamente pelo Spring, ou seja, não é necessário instanciar
 *             um Objeto ou indicar um uso específico (Service, Controller,
 *             Repository, entre outros).
 */
public class JwtService {
	/**
	 * Responsável por criar e validar o TokenJWT(ele é criado durante o processo de
	 * autenticação(login) do usuário e será validado em TODAS as requisições HTTP
	 * enviadas para os endpoints protegidos(lá na classe BasicSecurityConfig)
	 */
	
	public static final String SECRET = "e003fa4df0c78740809b1872d55c54d9a8f2e1c736f7e1aed938ce119e93e215";
	//Está armazenando a chave de assinatura do Token JWT,foi definido como final porque ele NUNCA se alterará.
	//Foi definido como static porque deve estar associado apenas a essa classe,é uma variavel da classe e nao do objeto
	//Para gerar uma , entrar no site https://www.keygen.io/#fakeLink e ir na opção SHA 256-bit Key
	

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey()).build()
				.parseClaimsJws(token).getBody();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(userName)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
					.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}
}
