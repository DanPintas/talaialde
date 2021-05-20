package es.danpintas.mvp;

import es.danpintas.talaialde.constants.Messages;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractView
    <P extends Presenter<?, ? extends View<P>>>
    implements View<P> {

  private P presenter;

  @Autowired
  private Locale locale;

  @Override
  public final P presenter() {
    return presenter;
  }

  @SuppressWarnings("unchecked")
  @Override
  public final void setPresenter(Presenter<?, ?> presenter) {
    this.presenter = (P) presenter;
  }

  @Override
  public final String getMessage(String id, Object... args) {
    return Messages.getMessage(id, args, locale);
  }

}
