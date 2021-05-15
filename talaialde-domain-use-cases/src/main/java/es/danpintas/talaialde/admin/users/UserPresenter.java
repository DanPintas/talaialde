package es.danpintas.talaialde.admin.users;

import java.util.List;
import java.util.Map;

import es.danpintas.mvp.Presenter;

public interface UserPresenter 
        extends Presenter<UserService, UserView> {

    List<JpaUser> findAllUsers();
    
    JpaUser saveUser(JpaUser u);
    
    void deleteUser(JpaUser u);
    
    Map<JpaRole, String> getRoleMap();

}
