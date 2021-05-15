package es.danpintas.talaialde.main;

import es.danpintas.mvp.Presenter;
import es.danpintas.mvp.View;

public interface MainPresenter extends Presenter<MainService, MainView> {

    Presenter<?, ?> openPresenter(Class<? extends Presenter<?, ?>> tipoAccion,
            String titulo);

    void closePresenter(String titulo);

    void logout();

    View<?> getView(String title);

}
