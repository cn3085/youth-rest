package org.youth.api.config.persistance;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.youth.api.entity.UserEntity;

@EnableJpaAuditing
@Configuration
public class JpaAuditingConfig implements AuditorAware<String>{

	@Override
	public Optional<String> getCurrentAuditor() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(authentication == null || !authentication.isAuthenticated()) {
			return Optional.ofNullable(null);
		}
		
		UserEntity user = (UserEntity)authentication.getPrincipal();
		return Optional.of(user.getUsername());
	}
}
