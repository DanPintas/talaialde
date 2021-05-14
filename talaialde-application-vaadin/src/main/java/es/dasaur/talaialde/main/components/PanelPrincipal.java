package es.dasaur.talaialde.main.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

public class PanelPrincipal extends CustomComponent {

    private static final long serialVersionUID = 5737161845507077916L;
    
    public PanelPrincipal() {
        
        AppLogo logo = new AppLogo();
        
        VerticalLayout layout = new VerticalLayout(logo);
        layout.setWidth("100%");
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setComponentAlignment(logo, Alignment.TOP_CENTER);
        
        setCompositionRoot(layout);
    }

    
}
