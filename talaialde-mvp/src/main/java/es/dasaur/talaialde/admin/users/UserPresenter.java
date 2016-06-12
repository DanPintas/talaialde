package es.dasaur.talaialde.admin.users;

import java.util.List;
import java.util.Map;

import es.dasaur.mvp.Presenter;
import es.dasaur.talaialde.admin.users.User;

public interface UserPresenter 
        extends Presenter<UserService, UserView> {

    List<User> findAllUsers();
    
    User saveUser(User u);
    
    void deleteUser(User u);
    
    Map<Role, String> getRoleMap();

}
