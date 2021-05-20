package es.danpintas.talaialde.admin.roles;

import es.danpintas.talaialde.admin.users.JpaAuth;
import es.danpintas.talaialde.admin.users.JpaRole;
import java.util.List;
import java.util.Map;

public interface RoleService {

  List<JpaRole> getRoles();

  List<JpaAuth> getAuths();

  Map<JpaRole, List<JpaAuth>> getRelations();

  void saveRelations(Map<JpaRole, List<JpaAuth>> relations);

}
