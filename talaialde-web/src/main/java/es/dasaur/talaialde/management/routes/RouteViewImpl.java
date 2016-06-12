package es.dasaur.talaialde.management.routes;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import es.dasaur.annotations.View;
import es.dasaur.talaialde.components.MasterDetail;
import es.dasaur.talaialde.components.MasterDetail.MasterDetailTextSupplier;
import es.dasaur.talaialde.constants.Formats;
import es.dasaur.talaialde.constants.Messages;
import es.dasaur.talaialde.users.security.SecurityUtils;
import es.dasaur.vaadin.components.BeanForm.BeanFormTextSupplier;
import es.dasaur.vaadin.mvp.VaadinAbstractView;

@View
public class RouteViewImpl 
        extends VaadinAbstractView<RoutePresenter>
        implements RouteView {

    private static final long serialVersionUID = 8347090806955125286L;
    
    @Autowired
    private MasterDetailTextSupplier mdTextSupplier;
    @Autowired
    private BeanFormTextSupplier formTextSupplier;

    private MasterDetail<Route> md;

    @Override
    public void init() {
        md = new MasterDetail<>(Route.class, 
                presenter()::findAllRoutes, 
                presenter()::saveRoute,
                presenter()::removeRoute,
                mdTextSupplier, formTextSupplier);
        
        md.setEditable(SecurityUtils.hasAuthority("AUTH_WRITE"));
        
        md.setVisibleColumns(
                Route.PROP_ORIGIN, 
                Route.PROP_DESTINY,
                Route.PROP_KM,
                Route.PROP_HOUR_FEE,
                Route.PROP_TON_FEE,
                Route.PROP_TRIP_FEE);
        md.setColumnHeaders(
                getMessage(Messages.PROP_ORIGIN),
                getMessage(Messages.PROP_DESTINY),
                getMessage(Messages.PROP_KM),
                getMessage(Messages.PROP_HOUR_FEE),
                getMessage(Messages.PROP_TON_FEE),
                getMessage(Messages.PROP_TRIP_FEE));
        md.setVisibleFields(
                Route.PROP_ORIGIN, 
                Route.PROP_DESTINY,
                Route.PROP_KM,
                Route.PROP_HOUR_FEE,
                Route.PROP_TON_FEE,
                Route.PROP_TRIP_FEE);
        md.setFieldCaptions(
                getMessage(Messages.PROP_ORIGIN),
                getMessage(Messages.PROP_DESTINY),
                getMessage(Messages.PROP_KM),
                getMessage(Messages.PROP_HOUR_FEE),
                getMessage(Messages.PROP_TON_FEE),
                getMessage(Messages.PROP_TRIP_FEE));
        
        md.addGeneratedColumn(Route.PROP_KM, (t, i, p) -> generateColumn(i, p));
        md.addGeneratedColumn(Route.PROP_HOUR_FEE, (t, i, p) -> generateColumn(i, p));
        md.addGeneratedColumn(Route.PROP_TON_FEE, (t, i, p) -> generateColumn(i, p));
        md.addGeneratedColumn(Route.PROP_TRIP_FEE, (t, i, p) -> generateColumn(i, p));
        
        md.refresh();
        setCompositionRoot(md);
        setSizeFull();
    }

    private Object generateColumn(Object itemId, Object propertyId) {
        Object value;
        BigDecimal bd;
        Route route = (Route) itemId;
        switch(propertyId.toString()){
        case Route.PROP_KM:
            bd = route.getKm();
            break;
        case Route.PROP_HOUR_FEE:
            bd = route.getHourFee();
            break;
        case Route.PROP_TON_FEE:
            bd = route.getTonFee();
            break;
        case Route.PROP_TRIP_FEE:
            bd = route.getTripFee();
            break;
        default:
            bd = null;
        }
        value = bd == null ? null : Formats.NUMBER.format(bd.doubleValue());
        return value;
    }

}
