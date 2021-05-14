package es.dasaur.talaialde.admin.roles;

import java.util.List;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import es.dasaur.annotations.View;
import es.dasaur.talaialde.admin.users.JpaAuth;
import es.dasaur.talaialde.admin.users.JpaRole;
import es.dasaur.talaialde.constants.Messages;
import es.dasaur.vaadin.components.RelationTable;
import es.dasaur.vaadin.mvp.VaadinAbstractView;
import es.dasaur.vaadin.utils.NotificationUtils;

@View
public class RoleViewImpl 
        extends VaadinAbstractView<RolePresenter>
        implements RoleView {

    private static final long serialVersionUID = 7042705179582398055L;
    
    private transient List<JpaRole> roles;
    private transient List<JpaAuth> auths;
    
    private RelationTable<JpaRole, JpaAuth> relationTable;
    
    @Override
    public void init() {
        roles = presenter().getRoles();
        auths = presenter().getAuths();
        
        relationTable = new RelationTable<>(roles, auths,
                r -> getMessage(r.getName()), a -> getMessage(a.getName()));
        relationTable.setSizeFull();
        
        Button btSave = new Button(getMessage(Messages.PROMPT_SAVE), 
                FontAwesome.SAVE);
        btSave.addClickListener(event -> guardarRelaciones());
        
        HorizontalLayout actions = new HorizontalLayout(btSave);
        actions.setSpacing(true);
        
        VerticalLayout layout = new VerticalLayout(relationTable, actions);
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();
        layout.setExpandRatio(relationTable, 1);
        layout.setComponentAlignment(actions, Alignment.MIDDLE_RIGHT);
        setCompositionRoot(layout);
        setSizeFull();
        
        relationTable.setRelations(presenter().getRelations());
    }
    
    private void guardarRelaciones() {
        presenter().saveRelations(relationTable.getRelations());
        NotificationUtils.showMessage(null, getMessage(
                Messages.PROMPT_SAVED_DATA));
    }

}
