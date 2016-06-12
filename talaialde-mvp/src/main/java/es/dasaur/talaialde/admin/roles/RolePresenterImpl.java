package es.dasaur.talaialde.admin.roles;

import java.util.List;
import java.util.Map;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;
import es.dasaur.talaialde.admin.users.Auth;
import es.dasaur.talaialde.admin.users.Role;

@Presenter
public class RolePresenterImpl 
        extends AbstractPresenter<RoleService, RoleView>
        implements RolePresenter{

    @Override
    public List<Role> getRoles() {
        return model().getRoles();
    }

    @Override
    public List<Auth> getAuths() {
        return model().getAuths();
    }

    @Override
    public Map<Role, List<Auth>> getRelations() {
        return model().getRelations();
    }

    @Override
    public void saveRelations(Map<Role, List<Auth>> relations) {
        model().saveRelations(relations);
    }

}
