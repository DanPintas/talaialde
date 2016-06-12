package es.dasaur.talaialde.admin.roles;

import java.util.List;
import java.util.Map;

import es.dasaur.mvp.Presenter;
import es.dasaur.talaialde.admin.users.Auth;
import es.dasaur.talaialde.admin.users.Role;

public interface RolePresenter 
        extends Presenter<RoleService, RoleView> {

    List<Role> getRoles();

    List<Auth> getAuths();

    Map<Role, List<Auth>> getRelations();

    void saveRelations(Map<Role, List<Auth>> relations);

}
