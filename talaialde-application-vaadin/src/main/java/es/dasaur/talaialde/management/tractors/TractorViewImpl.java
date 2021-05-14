package es.dasaur.talaialde.management.tractors;

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
        switch(propertyId.toString()){
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
