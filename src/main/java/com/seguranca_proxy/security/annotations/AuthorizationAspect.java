package com.seguranca_proxy.security.annotations;

import com.seguranca_proxy.security.Authentication;
import com.seguranca_proxy.security.GenericRole;
import com.seguranca_proxy.security.SecurityContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.List;

@Aspect
@Component
public class AuthorizationAspect {

    private final SecurityContext securityContext;

    public AuthorizationAspect(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @Around("@annotation(hasRole)")
    public Object authorize(ProceedingJoinPoint joinPoint, HasRole hasRole) throws Throwable {

        Authentication auth = securityContext.getAuthentication();

        if (auth == null) {
            throw new RuntimeException("Unauthorized");
        }

        var userRoles = auth.getGenericUser().getRoles()
                .stream()
                .map(GenericRole::getRoleName)
                .toList();

        boolean autorizado = userRoles.stream()
                .anyMatch(role -> List.of(hasRole.value()).contains(role));

        if (!autorizado) {
            throw new RuntimeException("Forbidden");
        }

        return joinPoint.proceed();
    }
}