package es.danpintas.talaialde.management.tractors;

import es.danpintas.annotations.View;
import es.danpintas.talaialde.components.MasterDetail;
import es.danpintas.talaialde.components.MasterDetail.MasterDetailTextSupplier;
import es.danpintas.talaialde.constants.Formats;
import es.danpintas.talaialde.constants.Messages;
import es.danpintas.talaialde.users.security.SecurityUtils;
import es.danpintas.vaadin.components.BeanForm.BeanFormTextSupplier;
import es.danpintas.vaadin.mvp.VaadinAbstractView;
import org.springframework.beans.factory.annotation.Autowired;

@View
public class TractorViewImpl
    extends VaadinAbstractView<TractorPresenter>
    implements TractorView {

  private static final long serialVersionUID = 8347090806955125286L;

  @Autowired
  private MasterDetailTextSupplier mdTextSupplier;

  @Autowired
  private BeanFormTextSupplier formTextSupplier;

  private MasterDetail<JpaTractor> md;

  @Override
  public void init() {
    md = new MasterDetail<>(JpaTractor.class,
        presenter()::findAllTractors,
        presenter()::saveTractor,
        presenter()::removeTractor,
        mdTextSupplier, formTextSupplier);

    md.setEditable(SecurityUtils.hasAuthority("AUTH_WRITE"));

    md.setVisibleColumns(
        JpaTractor.PROP_PLATE,
        JpaTractor.PROP_VI_EXPIRY,
        JpaTractor.PROP_USUAL_DRIVER);
    md.setColumnHeaders(
        getMessage(Messages.PROP_PLATE),
        getMessage(Messages.PROP_VI_EXPIRY),
        getMessage(Messages.PROP_USUAL_DRIVER));
    md.setVisibleFields(
        JpaTractor.PROP_PLATE,
        JpaTractor.PROP_VI_EXPIRY,
        JpaTractor.PROP_USUAL_DRIVER);
    md.setFieldCaptions(
        getMessage(Messages.PROP_PLATE),
        getMessage(Messages.PROP_VI_EXPIRY),
        getMessage(Messages.PROP_USUAL_DRIVER));

    md.addGeneratedColumn(JpaTractor.PROP_VI_EXPIRY, (t, i, p) -> generateColumn(i, p));

    md.refresh();
    setCompositionRoot(md);
    setSizeFull();
  }

  private Object generateColumn(Object itemId, Object propertyId) {
    Object value;
    JpaTractor tractor = (JpaTractor) itemId;
    switch (propertyId.toString()) {
      case JpaTractor.PROP_VI_EXPIRY:
        value = tractor.getVehicleInspectionExpiry() == null ? "" :
            Formats.DATE.format(tractor.getVehicleInspectionExpiry());
        break;
      default:
        value = null;
    }
    return value;
  }

}
