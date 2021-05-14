package es.dasaur.talaialde.admin.roles;

import java.util.List;
import java.util.Map;

import es.dasaur.talaialde.admin.users.JpaAuth;
import es.dasaur.talaialde.admin.users.JpaRole;

public interface RoleService {

    List<JpaRole> getRoles();

    List<JpaAuth> getAuths();

    Map<JpaRole, List<JpaAuth>> getRelations();

    void saveRelations(Map<JpaRole, List<JpaAuth>> relations);
    
}
