package es.danpintas.talaialde.users;

import es.danpintas.mvp.AbstractPresenter;
import es.danpintas.talaialde.constants.Messages;
import es.danpintas.talaialde.users.security.LoginErrorException;
import es.danpintas.talaialde.users.security.SecurityUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class LoginPresenterImpl
    extends AbstractPresenter<LoginService, LoginView>
    implements LoginPresenter {

  @Override
  public boolean login(String username, String password) {

    boolean ok = false;

    if (SecurityUtils.isLoggedIn()) {
      view().showWarning(
          getMessage(Messages.LOGIN_ERROR_TITLE),
          getMessage(Messages.LOGIN_ERROR_ALREADY_LOGGED));
      ok = true;
    } else {
      try {
        model().login(username, password);
        ok = true;
      } catch (LoginErrorException e) {
        switch (e.getErrorType()) {
          case NO_USER:
            view().showWarning(
                getMessage(Messages.LOGIN_ERROR_TITLE),
                getMessage(Messages.LOGIN_ERROR_NO_USER));
            break;
          case WRONG_PASSWORD:
            view().showWarning(
                getMessage(Messages.LOGIN_ERROR_TITLE),
                getMessage(Messages.LOGIN_ERROR_WRONG_PASSWORD));
            break;
        }
      } catch (Exception e) {
        view().showWarning(
            getMessage(Messages.LOGIN_ERROR_TITLE),
            getMessage(Messages.LOGIN_ERROR_EXCEPTION));
        e.printStackTrace();
      }
    }
    return ok;
  }

}