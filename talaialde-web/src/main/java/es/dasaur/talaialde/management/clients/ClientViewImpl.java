package es.dasaur.talaialde.management.clients;

import org.springframework.beans.factory.annotation.Autowired;

import es.dasaur.annotations.View;
import es.dasaur.talaialde.components.MasterDetail;
import es.dasaur.talaialde.components.MasterDetail.MasterDetailTextSupplier;
import es.dasaur.talaialde.constants.Messages;
import es.dasaur.talaialde.users.security.SecurityUtils;
import es.dasaur.vaadin.components.BeanForm.BeanFormTextSupplier;
import es.dasaur.vaadin.mvp.VaadinAbstractView;

@View
public class ClientViewImpl 
        extends VaadinAbstractView<ClientPresenter>
        implements ClientView {

    private static final long serialVersionUID = 8347090806955125286L;
    
    @Autowired
    private MasterDetailTextSupplier mdTextSupplier;
    @Autowired
    private BeanFormTextSupplier formTextSupplier;

    private MasterDetail<Client> md;

    @Override
    public void init() {
        md = new MasterDetail<>(Client.class, 
                presenter()::findAllClients, 
                presenter()::saveClient,
                presenter()::removeClient,
                mdTextSupplier, formTextSupplier);
        
        md.setEditable(SecurityUtils.hasAuthority("AUTH_WRITE"));
        
        md.setVisibleColumns(
                Client.PROP_NAME,
                Client.PROP_LOCALITY,
                Client.PROP_PHONE_NUMBER,
                Client.PROP_EMAIL,
                Client.PROP_CONTACT);
        md.setColumnHeaders(
                getMessage(Messages.PROP_NAME), 
                getMessage(Messages.PROP_LOCALITY),
                getMessage(Messages.PROP_PHONE_NUMBER),
                getMessage(Messages.PROP_EMAIL),
                getMessage(Messages.PROP_CONTACT));
        md.setVisibleFields(
                Client.PROP_TIN,
                Client.PROP_NAME,
                Client.PROP_LINE,
                Client.PROP_LOCALITY,
                Client.PROP_REGION,
                Client.PROP_PHONE_NUMBER,
                Client.PROP_EMAIL,
                Client.PROP_CONTACT,
                Client.PROP_ACCOUNT_NUMBER,
                Client.PROP_OBSERVATIONS);
        md.setFieldCaptions(
                getMessage(Messages.PROP_TIN),
                getMessage(Messages.PROP_NAME), 
                getMessage(Messages.PROP_ADDRESS),
                getMessage(Messages.PROP_LOCALITY),
                getMessage(Messages.PROP_REGION),
                getMessage(Messages.PROP_PHONE_NUMBER),
                getMessage(Messages.PROP_EMAIL),
                getMessage(Messages.PROP_CONTACT),
                getMessage(Messages.PROP_ACCOUNT_NUMBER),
                getMessage(Messages.PROP_OBSERVATIONS));

        md.refresh();
        setCompositionRoot(md);
        setSizeFull();
    }

}
