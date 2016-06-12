package es.dasaur.talaialde.admin.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;
import es.dasaur.talaialde.admin.users.User;

@Presenter
public class UserPresenterImpl 
        extends AbstractPresenter<UserService, UserView>
        implements UserPresenter{

    @Override
    public List<User> findAllUsers() {
        return model().findAllUsers();
    }

    @Override
    public User saveUser(User u) {
        return model().saveUser(u);
    }

    @Override
    public void deleteUser(User u) {
        model().deleteUser(u);
    }

    @Override
    public Map<Role, String> getRoleMap() {
        Map<Role, String> roleMap = new HashMap<>();
        model().getRoles().forEach(r -> roleMap.put(r, getMessage(r.getName())));
        return roleMap;
    }

}
