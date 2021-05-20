package es.danpintas.vaadin.mvp;

import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import es.danpintas.mvp.Presenter;
import es.danpintas.mvp.View;
import es.danpintas.talaialde.constants.Messages;
import es.danpintas.vaadin.utils.NotificationUtils;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class VaadinAbstractView
    <P extends Presenter<?, ? extends View<P>>>
    extends CustomComponent
    implements View<P>, Component {

  private static final long serialVersionUID = -1459845644525324349L;

  private transient P presenter;

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

  @Override
  public final void showWarning(String caption, String description) {
    NotificationUtils.showWarning(caption, description);
  }

}
