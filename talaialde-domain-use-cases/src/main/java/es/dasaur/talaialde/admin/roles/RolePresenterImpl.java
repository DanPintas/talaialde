package es.dasaur.talaialde.admin.roles;

import java.util.List;
import java.util.Map;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;
import es.dasaur.talaialde.admin.users.JpaAuth;
import es.dasaur.talaialde.admin.users.JpaRole;

@Presenter
public class RolePresenterImpl 
        extends AbstractPresenter<RoleService, RoleView>
        implements RolePresenter{

    @Override
    public List<JpaRole> getRoles() {
        return model().getRoles();
    }

    @Override
    public List<JpaAuth> getAuths() {
        return model().getAuths();
    }

    @Override
    public Map<JpaRole, List<JpaAuth>> getRelations() {
        return model().getRelations();
    }

    @Override
    public void saveRelations(Map<JpaRole, List<JpaAuth>> relations) {
        model().saveRelations(relations);
    }

}
