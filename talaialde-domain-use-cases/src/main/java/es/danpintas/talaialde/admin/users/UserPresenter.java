package es.danpintas.talaialde.admin.users;

import es.danpintas.mvp.Presenter;
import java.util.List;
import java.util.Map;

public interface UserPresenter
    extends Presenter<UserService, UserView> {

  List<JpaUser> findAllUsers();

  JpaUser saveUser(JpaUser u);

  void deleteUser(JpaUser u);

  Map<JpaRole, String> getRoleMap();

}
