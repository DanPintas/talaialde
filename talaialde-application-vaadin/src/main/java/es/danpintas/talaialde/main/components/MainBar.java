package es.danpintas.talaialde.main.components;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import es.danpintas.talaialde.Authorities;
import es.danpintas.talaialde.admin.users.UserPresenter;
import es.danpintas.talaialde.billing.bill.BillPresenter;
import es.danpintas.talaialde.billing.freebill.FreeBillPresenter;
import es.danpintas.talaialde.billing.freeline.FreeBillLinePresenter;
import es.danpintas.talaialde.billing.line.BillLinePresenter;
import es.danpintas.talaialde.constants.Messages;
import es.danpintas.talaialde.main.MainPresenter;
import es.danpintas.talaialde.management.clients.ClientPresenter;
import es.danpintas.talaialde.management.routes.RoutePresenter;
import es.danpintas.talaialde.management.tractors.TractorPresenter;
import es.danpintas.talaialde.users.security.SecurityUtils;

public class MainBar extends MenuBar {

    private static final long serialVersionUID = 9201983170322596096L;
    
    private final MenuItem billing;
    private MenuItem freeBilling;
    private final MenuItem management;
    private final MenuItem admin;

    public MainBar(MainPresenter presenter) {
        super();

        setWidth("100%");
                
        billing = addItem("Fact. vehículos",
                FontAwesome.FILE_TEXT_O, null);
        
        String tBillingNewLine = presenter.getMessage(Messages.MENU_BILLING_NEW_LINE);
        String tBillingSeeBill = presenter.getMessage(Messages.MENU_BILLING_SEE_BILL);
        
        billing.addItem(tBillingNewLine,  FontAwesome.PLUS, 
                i -> presenter.openPresenter(
                        BillLinePresenter.class, tBillingNewLine));
        
        billing.addItem(tBillingSeeBill,  FontAwesome.FILE_TEXT_O, 
                i -> presenter.openPresenter(
                        BillPresenter.class, tBillingSeeBill));
        
        freeBilling = addItem("Fact. libre",
                FontAwesome.FILE_TEXT_O, null);
        
        freeBilling.addItem(tBillingNewLine + " (libre)",  FontAwesome.PLUS, 
                i -> presenter.openPresenter(
                        FreeBillLinePresenter.class, tBillingNewLine+ " (libre)"));
        
        freeBilling.addItem(tBillingSeeBill + " (libre)",  FontAwesome.FILE_TEXT_O, 
                i -> presenter.openPresenter(
                        FreeBillPresenter.class, tBillingSeeBill+ " (libre)"));
        
        management = addItem(presenter.getMessage(Messages.MENU_MANAGEMENT),
                FontAwesome.CONNECTDEVELOP, null);
        
        String tManagementClients = presenter.getMessage(Messages.MENU_MANAGEMENT_CLIENTS);
        management.addItem(tManagementClients,  FontAwesome.USERS, 
                i -> presenter.openPresenter(
                        ClientPresenter.class, tManagementClients));
        
        String tManagementRoutes = presenter.getMessage(Messages.MENU_MANAGEMENT_ROUTES);
        management.addItem(tManagementRoutes, FontAwesome.ROAD, 
                i -> presenter.openPresenter(
                        RoutePresenter.class, tManagementRoutes));
        
        String tManagementTractors = presenter.getMessage(Messages.MENU_MANAGEMENT_TRACTORS);
        management.addItem(tManagementTractors, FontAwesome.TRUCK, 
                i -> presenter.openPresenter(
                        TractorPresenter.class, tManagementTractors));
        
        
        admin = addItem(presenter.getMessage(Messages.MENU_ADMIN),
                FontAwesome.COGS, null);
        
        String tAdminUsers = presenter.getMessage(Messages.MENU_ADMIN_USERS);
        admin.addItem(tAdminUsers, FontAwesome.USERS,
                i -> presenter.openPresenter(
                        UserPresenter.class, tAdminUsers));
        
//        String tAdminRoles = presenter.getMessage(MENU_ADMIN_ROLES);
//        admin.addItem(tAdminRoles, FontAwesome.SITEMAP,
//                i -> presenter.openPresenter(
//                        RolePresenter.class, tAdminRoles));
        
        initAuths();
        
    }

    private void initAuths() {
        admin.setVisible(SecurityUtils.hasAuthority(Authorities.ADMIN));
    }

}
