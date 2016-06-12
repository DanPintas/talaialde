package es.dasaur.talaialde.admin.users;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.ui.CheckBox;

import es.dasaur.annotations.View;
import es.dasaur.talaialde.components.MasterDetail;
import es.dasaur.talaialde.components.MasterDetail.MasterDetailTextSupplier;
import es.dasaur.talaialde.constants.Messages;
import es.dasaur.talaialde.users.security.SecurityUtils;
import es.dasaur.vaadin.components.BeanForm.BeanFormTextSupplier;
import es.dasaur.vaadin.mvp.VaadinAbstractView;

@View
public class UserViewImpl 
        extends VaadinAbstractView<UserPresenter>
        implements UserView {

    private static final long serialVersionUID = 7042705179582398055L;
    
    @Autowired
    private MasterDetailTextSupplier mdTextSupplier;
    @Autowired
    private BeanFormTextSupplier formTextSupplier;

    private MasterDetail<User> md;

    @Override
    public void init() {
        md = new MasterDetail<>(User.class, 
                presenter()::findAllUsers, 
                presenter()::saveUser,
                presenter()::deleteUser,
                mdTextSupplier, formTextSupplier);
        
        md.setEditable(SecurityUtils.hasAuthority("AUTH_WRITE"));
        
        md.addGeneratedColumn(User.PROP_ROLE, 
                (s, i, p) -> presenter().getRoleMap().get(((User)i).getRole()));
        md.addGeneratedColumn(User.PROP_ACTIVE, (s, i, p) -> {
            CheckBox cb = new CheckBox();
            cb.setValue(((User)i).getActive());
            cb.setReadOnly(true);
            return cb;
        });
        
        md.setVisibleColumns(
                User.PROP_USERNAME, 
                User.PROP_ROLE,
                User.PROP_NAME,
                User.PROP_SURNAME,
                User.PROP_EMAIL);
        md.setColumnHeaders(
                getMessage(Messages.PROP_USERNAME),
                getMessage(Messages.PROP_ROLE),
                getMessage(Messages.PROP_NAME),
                getMessage(Messages.PROP_SURNAME),
                getMessage(Messages.PROP_EMAIL));
        md.setVisibleFields(
                User.PROP_USERNAME,
                User.PROP_PASSWORD, 
                User.PROP_ROLE,
                User.PROP_NAME,
                User.PROP_SURNAME,
                User.PROP_EMAIL);
        md.setFieldCaptions(
                getMessage(Messages.PROP_USERNAME),
                getMessage(Messages.PROP_PASSWORD),
                getMessage(Messages.PROP_ROLE),
                getMessage(Messages.PROP_NAME),
                getMessage(Messages.PROP_SURNAME),
                getMessage(Messages.PROP_EMAIL));
        
        Map<String, Supplier<Map<?, String>>> linkMap = new HashMap<>();
        linkMap.put(User.PROP_ROLE, presenter()::getRoleMap);
        md.setLinkMap(linkMap);
        
        md.refresh();
        
        setCompositionRoot(md);
        setSizeFull();
    }

}
