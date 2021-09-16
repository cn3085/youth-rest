package org.youth.api.config.security;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	private String secretKey = Base64.getEncoder().encodeToString("YOUTHCAFEAPPLICATION".getBytes());
	
	private long tokenValidTime = 60 * 60 * 1000L * 24;
	
	private final UserDetailsService userDetailService;
	
	public String createToken(String userId, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userId);
		claims.put("roles", roles);
		Date now = new Date();
		
		return Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(now)
					.setExpiration(new Date(now.getTime() + tokenValidTime))
					.signWith(SignatureAlgorithm.HS256, secretKey)
					.compact();
				
	}
	
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailService.loadUserByUsername(this.getUserId(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}
	
	public String getUserId(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}
	
	public String resolveToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}
	
	public boolean validateToken(String jwtToken) {
		Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
		return claims.getBody().getExpiration().after(new Date());
	}
}
