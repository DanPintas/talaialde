package es.danpintas.mvp;

/**
 * MVP view, matching {@link Model} and {@link Presenter} <br> Implementations of interfaces extending this class are encouraged to extend
 * {@link AbstractView} or its appropriate subclass.
 *
 * @author dsalas
 */
public interface View
    <P extends Presenter<?, ? extends View<P>>> {

  P presenter();

  void setPresenter(Presenter<?, ?> presenter);

  void init();

  String getMessage(String id, Object... args);

  void showWarning(String caption, String description);

}
