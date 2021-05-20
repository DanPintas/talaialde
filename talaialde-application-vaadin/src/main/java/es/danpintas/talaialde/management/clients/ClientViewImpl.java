package es.danpintas.talaialde.management.clients;

import es.danpintas.annotations.View;
import es.danpintas.talaialde.components.MasterDetail;
import es.danpintas.talaialde.components.MasterDetail.MasterDetailTextSupplier;
import es.danpintas.talaialde.constants.Messages;
import es.danpintas.talaialde.users.security.SecurityUtils;
import es.danpintas.vaadin.components.BeanForm.BeanFormTextSupplier;
import es.danpintas.vaadin.mvp.VaadinAbstractView;
import org.springframework.beans.factory.annotation.Autowired;

@View
public class ClientViewImpl
    extends VaadinAbstractView<ClientPresenter>
    implements ClientView {

  private static final long serialVersionUID = 8347090806955125286L;

  @Autowired
  private MasterDetailTextSupplier mdTextSupplier;

  @Autowired
  private BeanFormTextSupplier formTextSupplier;

  private MasterDetail<JpaClient> md;

  @Override
  public void init() {
    md = new MasterDetail<>(JpaClient.class,
        presenter()::findAllClients,
        presenter()::saveClient,
        presenter()::removeClient,
        mdTextSupplier, formTextSupplier);

    md.setEditable(SecurityUtils.hasAuthority("AUTH_WRITE"));

    md.setVisibleColumns(
        JpaClient.PROP_NAME,
        JpaClient.PROP_LOCALITY,
        JpaClient.PROP_PHONE_NUMBER,
        JpaClient.PROP_EMAIL,
        JpaClient.PROP_CONTACT);
    md.setColumnHeaders(
        getMessage(Messages.PROP_NAME),
        getMessage(Messages.PROP_LOCALITY),
        getMessage(Messages.PROP_PHONE_NUMBER),
        getMessage(Messages.PROP_EMAIL),
        getMessage(Messages.PROP_CONTACT));
    md.setVisibleFields(
        JpaClient.PROP_TIN,
        JpaClient.PROP_NAME,
        JpaClient.PROP_LINE,
        JpaClient.PROP_LOCALITY,
        JpaClient.PROP_REGION,
        JpaClient.PROP_PHONE_NUMBER,
        JpaClient.PROP_EMAIL,
        JpaClient.PROP_CONTACT,
        JpaClient.PROP_ACCOUNT_NUMBER,
        JpaClient.PROP_OBSERVATIONS);
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
