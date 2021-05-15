package es.danpintas.vaadin.utils;

import java.net.URI;

import com.vaadin.server.Page;
import com.vaadin.ui.UI;

public class NavigationUtils {
    
    private NavigationUtils() {
        // utils class
    }

    public static final void refresh() {
        URI url = Page.getCurrent().getLocation();
        UI.getCurrent().getPage().setLocation(url);
    }

}
