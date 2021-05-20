package es.danpintas.talaialde.admin.users;

import com.vaadin.ui.CheckBox;
import es.danpintas.annotations.View;
import es.danpintas.talaialde.components.MasterDetail;
import es.danpintas.talaialde.components.MasterDetail.MasterDetailTextSupplier;
import es.danpintas.talaialde.constants.Messages;
import es.danpintas.talaialde.users.security.SecurityUtils;
import es.danpintas.vaadin.components.BeanForm.BeanFormTextSupplier;
import es.danpintas.vaadin.mvp.VaadinAbstractView;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;

@View
public class UserViewImpl
    extends VaadinAbstractView<UserPresenter>
    implements UserView {

  private static final long serialVersionUID = 7042705179582398055L;

  @Autowired
  private MasterDetailTextSupplier mdTextSupplier;

  @Autowired
  private BeanFormTextSupplier formTextSupplier;

  private MasterDetail<JpaUser> md;

  @Override
  public void init() {
    md = new MasterDetail<>(JpaUser.class,
        presenter()::findAllUsers,
        presenter()::saveUser,
        presenter()::deleteUser,
        mdTextSupplier, formTextSupplier);

    md.setEditable(SecurityUtils.hasAuthority("AUTH_WRITE"));

    md.addGeneratedColumn(JpaUser.PROP_ROLE,
        (s, i, p) -> presenter().getRoleMap().get(((JpaUser) i).getRole()));
    md.addGeneratedColumn(JpaUser.PROP_ACTIVE, (s, i, p) -> {
      CheckBox cb = new CheckBox();
      cb.setValue(((JpaUser) i).getActive());
      cb.setReadOnly(true);
      return cb;
    });

    md.setVisibleColumns(
        JpaUser.PROP_USERNAME,
        JpaUser.PROP_ROLE,
        JpaUser.PROP_NAME,
        JpaUser.PROP_SURNAME,
        JpaUser.PROP_EMAIL);
    md.setColumnHeaders(
        getMessage(Messages.PROP_USERNAME),
        getMessage(Messages.PROP_ROLE),
        getMessage(Messages.PROP_NAME),
        getMessage(Messages.PROP_SURNAME),
        getMessage(Messages.PROP_EMAIL));
    md.setVisibleFields(
        JpaUser.PROP_USERNAME,
        JpaUser.PROP_PASSWORD,
        JpaUser.PROP_ROLE,
        JpaUser.PROP_NAME,
        JpaUser.PROP_SURNAME,
        JpaUser.PROP_EMAIL);
    md.setFieldCaptions(
        getMessage(Messages.PROP_USERNAME),
        getMessage(Messages.PROP_PASSWORD),
        getMessage(Messages.PROP_ROLE),
        getMessage(Messages.PROP_NAME),
        getMessage(Messages.PROP_SURNAME),
        getMessage(Messages.PROP_EMAIL));

    Map<String, Supplier<Map<?, String>>> linkMap = new HashMap<>();
    linkMap.put(JpaUser.PROP_ROLE, presenter()::getRoleMap);
    md.setLinkMap(linkMap);

    md.refresh();

    setCompositionRoot(md);
    setSizeFull();
  }

}
