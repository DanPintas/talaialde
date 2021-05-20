package es.danpintas.mvp;

import es.danpintas.talaialde.main.MainPresenter;

public interface Presenter
    <M, V extends View<? extends Presenter<M, V>>> {

  M model();

  V view();

  void init();

  String getMessage(String id, Object... args);

  String getTitle();

  void setTitle(String title);

  MainPresenter getMainPresenter();

  void setMainPresenter(MainPresenter mainPresenter);
}
