package es.danpintas.vaadin.security;

import es.danpintas.vaadin.utils.NavigationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

public class VaadinSecurityUtils {
    
    private static final Logger LOG = 
            LoggerFactory.getLogger(VaadinSecurityUtils.class);
    
    private VaadinSecurityUtils() {
        // utils
    }
    
    public static final void logout() {
        invalidarServletSession();
        UI.getCurrent().getSession().close();
        NavigationUtils.refresh();
    }

    private static final void invalidarServletSession() {
        try {
            VaadinService.getCurrentRequest().getWrappedSession().invalidate();
        } catch (Exception e) {
            LOG.warn("Aviso: Hubo algún problema al invalidar la sesión: " + e);
        }
    }
}
