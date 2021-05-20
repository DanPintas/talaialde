package es.danpintas.talaialde.admin.roles;

import es.danpintas.annotations.Presenter;
import es.danpintas.mvp.AbstractPresenter;
import es.danpintas.talaialde.admin.users.JpaAuth;
import es.danpintas.talaialde.admin.users.JpaRole;
import java.util.List;
import java.util.Map;

@Presenter
public class RolePresenterImpl
    extends AbstractPresenter<RoleService, RoleView>
    implements RolePresenter {

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
