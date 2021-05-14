package es.dasaur.talaialde.users.security;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    
    private static final Logger LOG = 
            LoggerFactory.getLogger(SecurityUtils.class);
    
    private SecurityUtils() {
        // utils
    }
    
    public static final boolean isLoggedIn() {
        return SecurityContextHolder.getContext().getAuthentication() != null
                && SecurityContextHolder.getContext().getAuthentication()
                        .isAuthenticated()
                && !SecurityContextHolder.getContext().getAuthentication()
                        .getName().equals("anonymousUser");
    }
    
    public static final void logout() {
        if (SecurityContextHolder.getContext() != null &&
            SecurityContextHolder.getContext().getAuthentication() !=null) {

            String username = 
                    SecurityContextHolder.getContext().
                    getAuthentication().getName();
            
            SecurityContextHolder.clearContext();
            
            if (username!=null) {
                LOG.info("Usuario '" + username +
                         "' finaliza sesión con éxito.");
            } else {
                LOG.warn("Usuario finaliza sesión. Pero se desconoce su " +
                         "identificador. Puede ser debido a una petición de " +
                         "cierre de sesión sin tener una sesión activa.");
            }

        } else if (SecurityContextHolder.getContext() != null) {
            SecurityContextHolder.clearContext();
            LOG.warn("Requerida limpieza del contexto de seguridad. " +
                     "Sin embargo, este contexto ya estaba desautenticado.");
        }
    }

    public static String getUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    
    public static boolean hasAuthority(String name) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(c -> name.equals(c.getAuthority()));
    }
    
    public static boolean hasAnyAuthority(String... names) {
        return SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(c -> Arrays.asList(names).contains(c.getAuthority()));
    }

}
