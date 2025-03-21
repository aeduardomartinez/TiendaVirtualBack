package com.loquierestecno.loquierestecnoBack.security;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.loquierestecno.loquierestecnoBack.constants.ConstantesGenerales;

/**
 * Implementation of {@link AuditorAware} based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return Optional.of(Optional.ofNullable(authentication.getName())).orElse( Optional.of(ConstantesGenerales.USERNAME_TRABAJOS_EN_BACKGROUND));
    }
}
