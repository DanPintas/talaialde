package es.dasaur.talaialde.users;

import es.dasaur.mvp.Presenter;

public interface LoginPresenter extends Presenter<LoginService, LoginView> {

    boolean login(String username, String password);
    
}
