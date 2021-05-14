package es.dasaur.talaialde.main.components;

import static es.dasaur.talaialde.constants.Messages.*;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;

import es.dasaur.talaialde.Authorities;
import es.dasaur.talaialde.admin.users.UserPresenter;
import es.dasaur.talaialde.billing.bill.BillPresenter;
import es.dasaur.talaialde.billing.freebill.FreeBillPresenter;
import es.dasaur.talaialde.billing.freeline.FreeBillLinePresenter;
import es.dasaur.talaialde.billing.line.BillLinePresenter;
import es.dasaur.talaialde.main.MainPresenter;
import es.dasaur.talaialde.management.clients.ClientPresenter;
import es.dasaur.talaialde.management.routes.RoutePresenter;
import es.dasaur.talaialde.management.tractors.TractorPresenter;
import es.dasaur.talaialde.users.security.SecurityUtils;

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
        
        String tBillingNewLine = presenter.getMessage(MENU_BILLING_NEW_LINE);
        String tBillingSeeBill = presenter.getMessage(MENU_BILLING_SEE_BILL);
        
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
        
        management = addItem(presenter.getMessage(MENU_MANAGEMENT),
                FontAwesome.CONNECTDEVELOP, null);
        
        String tManagementClients = presenter.getMessage(MENU_MANAGEMENT_CLIENTS);
        management.addItem(tManagementClients,  FontAwesome.USERS, 
                i -> presenter.openPresenter(
                        ClientPresenter.class, tManagementClients));
        
        String tManagementRoutes = presenter.getMessage(MENU_MANAGEMENT_ROUTES);
        management.addItem(tManagementRoutes, FontAwesome.ROAD, 
                i -> presenter.openPresenter(
                        RoutePresenter.class, tManagementRoutes));
        
        String tManagementTractors = presenter.getMessage(MENU_MANAGEMENT_TRACTORS);
        management.addItem(tManagementTractors, FontAwesome.TRUCK, 
                i -> presenter.openPresenter(
                        TractorPresenter.class, tManagementTractors));
        
        
        admin = addItem(presenter.getMessage(MENU_ADMIN),
                FontAwesome.COGS, null);
        
        String tAdminUsers = presenter.getMessage(MENU_ADMIN_USERS);
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
