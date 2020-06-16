package es.dasaur.talaialde.main;

import es.dasaur.mvp.View;

public interface MainView extends View<MainPresenter> {

    void addSubview(View<?> view, String titulo);

    void show(View<?> vistaAbierta);

    void closeView(String title);

}
