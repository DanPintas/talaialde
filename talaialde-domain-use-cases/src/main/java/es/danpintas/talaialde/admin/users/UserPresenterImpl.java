package es.danpintas.talaialde.admin.users;

import es.danpintas.annotations.Presenter;
import es.danpintas.mvp.AbstractPresenter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Presenter
public class UserPresenterImpl
    extends AbstractPresenter<UserService, UserView>
    implements UserPresenter {

  @Override
  public List<JpaUser> findAllUsers() {
    return model().findAllUsers();
  }

  @Override
  public JpaUser saveUser(JpaUser u) {
    return model().saveUser(u);
  }

  @Override
  public void deleteUser(JpaUser u) {
    model().deleteUser(u);
  }

  @Override
  public Map<JpaRole, String> getRoleMap() {
    Map<JpaRole, String> roleMap = new HashMap<>();
    model().getRoles().forEach(r -> roleMap.put(r, getMessage(r.getName())));
    return roleMap;
  }

}
