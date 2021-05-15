package es.danpintas.talaialde.users;

import es.danpintas.mvp.Presenter;

public interface LoginPresenter extends Presenter<LoginService, LoginView> {

    boolean login(String username, String password);
    
}
