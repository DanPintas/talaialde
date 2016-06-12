package es.dasaur.talaialde.main.components;

import static es.dasaur.talaialde.constants.ConstantesRecursos.RUTA_LOGO;

import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Image;

public class AppLogo extends CustomComponent {

    private static final long serialVersionUID = -1859076763310666508L;

    public AppLogo() {
        
        Image logo = new Image(null, new ThemeResource(RUTA_LOGO));
        CssLayout logoLayout = new CssLayout(logo);
        logoLayout.setWidth("100%");
        Responsive.makeResponsive(logoLayout);

        setCompositionRoot(logoLayout);
    }
    
}
