package com.mascotas.app.security.jwt;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.mascotas.app.security.dto.JwtDto;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.mascotas.app.security.models.MainUserEntity;
import com.mascotas.app.security.services.UserServiceImp;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

//Generar token : validación en la creación
@Component
public class JwtProvider {
	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private int expiration;
	@Autowired
	UserServiceImp userServiceImp;
	
	public String generateToken(Authentication authentication) {
		MainUserEntity usuarioPrincipal = (MainUserEntity) authentication.getPrincipal();
		List<String> roles = usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(
				Collectors.toList()
		);

		return Jwts.builder()
				.setSubject(usuarioPrincipal.getUsername())
				.claim("roles", roles)
				.claim("userId", usuarioPrincipal.getId())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
	//Obtiene nombreUsuario desde token
	public String getNombreUsuarioFromToken(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
	}

	public Long getUserIdFromToken(String token) throws ParseException {
		JWT jwt = JWTParser.parse(token);
		JWTClaimsSet claims = jwt.getJWTClaimsSet();
		return (Long) claims.getClaim("userId");
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Token mal formado");
		}catch (UnsupportedJwtException e) {
			logger.error("Token no soportado");
		}catch (ExpiredJwtException e) {
			logger.error("Token expirado");
		}catch (IllegalArgumentException e) {
			logger.error("Token vacio");
		}catch (SignatureException e) {
			logger.error("Fallo en la firma");
		}
		return false;
	}

	public String refreshToken(JwtDto jwtDTO) throws ParseException {
		JWT jwt = JWTParser.parse(jwtDTO.getToken());
		JWTClaimsSet claims = jwt.getJWTClaimsSet();
		String nombreUsuario = claims.getSubject();
		Long userId = (Long) claims.getClaim("userId");
		List<String> roles = (List<String>) claims.getClaim("roles");

		return Jwts.builder()
//				.setId(claims.getClaim('id'))
				.setSubject(nombreUsuario)
				.claim("roles", roles)
				.claim("userId", userId)
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
}