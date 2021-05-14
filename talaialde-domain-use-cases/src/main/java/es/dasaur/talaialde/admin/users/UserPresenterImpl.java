package es.dasaur.talaialde.admin.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;

@Presenter
public class UserPresenterImpl 
        extends AbstractPresenter<UserService, UserView>
        implements UserPresenter{

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
