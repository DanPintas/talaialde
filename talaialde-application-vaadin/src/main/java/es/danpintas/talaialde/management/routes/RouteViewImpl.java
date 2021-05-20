package es.danpintas.talaialde.management.routes;

import es.danpintas.annotations.View;
import es.danpintas.talaialde.components.MasterDetail;
import es.danpintas.talaialde.components.MasterDetail.MasterDetailTextSupplier;
import es.danpintas.talaialde.constants.Formats;
import es.danpintas.talaialde.constants.Messages;
import es.danpintas.talaialde.users.security.SecurityUtils;
import es.danpintas.vaadin.components.BeanForm.BeanFormTextSupplier;
import es.danpintas.vaadin.mvp.VaadinAbstractView;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;

@View
public class RouteViewImpl
    extends VaadinAbstractView<RoutePresenter>
    implements RouteView {

  private static final long serialVersionUID = 8347090806955125286L;

  @Autowired
  private MasterDetailTextSupplier mdTextSupplier;

  @Autowired
  private BeanFormTextSupplier formTextSupplier;

  private MasterDetail<JpaRoute> md;

  @Override
  public void init() {
    md = new MasterDetail<>(JpaRoute.class,
        presenter()::findAllRoutes,
        presenter()::saveRoute,
        presenter()::removeRoute,
        mdTextSupplier, formTextSupplier);

    md.setEditable(SecurityUtils.hasAuthority("AUTH_WRITE"));

    md.setVisibleColumns(
        JpaRoute.PROP_ORIGIN,
        JpaRoute.PROP_DESTINY,
        JpaRoute.PROP_KM,
        JpaRoute.PROP_HOUR_FEE,
        JpaRoute.PROP_TON_FEE,
        JpaRoute.PROP_TRIP_FEE);
    md.setColumnHeaders(
        getMessage(Messages.PROP_ORIGIN),
        getMessage(Messages.PROP_DESTINY),
        getMessage(Messages.PROP_KM),
        getMessage(Messages.PROP_HOUR_FEE),
        getMessage(Messages.PROP_TON_FEE),
        getMessage(Messages.PROP_TRIP_FEE));
    md.setVisibleFields(
        JpaRoute.PROP_ORIGIN,
        JpaRoute.PROP_DESTINY,
        JpaRoute.PROP_KM,
        JpaRoute.PROP_HOUR_FEE,
        JpaRoute.PROP_TON_FEE,
        JpaRoute.PROP_TRIP_FEE);
    md.setFieldCaptions(
        getMessage(Messages.PROP_ORIGIN),
        getMessage(Messages.PROP_DESTINY),
        getMessage(Messages.PROP_KM),
        getMessage(Messages.PROP_HOUR_FEE),
        getMessage(Messages.PROP_TON_FEE),
        getMessage(Messages.PROP_TRIP_FEE));

    md.addGeneratedColumn(JpaRoute.PROP_KM, (t, i, p) -> generateColumn(i, p));
    md.addGeneratedColumn(JpaRoute.PROP_HOUR_FEE, (t, i, p) -> generateColumn(i, p));
    md.addGeneratedColumn(JpaRoute.PROP_TON_FEE, (t, i, p) -> generateColumn(i, p));
    md.addGeneratedColumn(JpaRoute.PROP_TRIP_FEE, (t, i, p) -> generateColumn(i, p));

    md.refresh();
    setCompositionRoot(md);
    setSizeFull();
  }

  private Object generateColumn(Object itemId, Object propertyId) {
    Object value;
    BigDecimal bd;
    JpaRoute route = (JpaRoute) itemId;
    switch (propertyId.toString()) {
      case JpaRoute.PROP_KM:
        bd = route.getKm();
        break;
      case JpaRoute.PROP_HOUR_FEE:
        bd = route.getHourFee();
        break;
      case JpaRoute.PROP_TON_FEE:
        bd = route.getTonFee();
        break;
      case JpaRoute.PROP_TRIP_FEE:
        bd = route.getTripFee();
        break;
      default:
        bd = null;
    }
    value = bd == null ? null : Formats.NUMBER.format(bd.doubleValue());
    return value;
  }

}
