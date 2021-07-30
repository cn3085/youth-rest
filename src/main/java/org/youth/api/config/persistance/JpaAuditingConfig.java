package org.youth.api.config.persistance;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@EnableJpaAuditing
@Configuration
public class JpaAuditingConfig implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || !authentication.isAuthenticated()) {
			return Optional.ofNullable(null);
		}
		
		return Optional.of("admin"); //TODO : awt user로 바꾸기
	}
}
