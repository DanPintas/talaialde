package es.dasaur.talaialde.main;

import es.dasaur.mvp.Presenter;
import es.dasaur.mvp.View;

public interface MainPresenter extends Presenter<MainService, MainView> {

    Presenter<?, ?> openPresenter(Class<? extends Presenter<?, ?>> tipoAccion,
            String titulo);

    void closePresenter(String titulo);

    void logout();

    View<?> getView(String title);

}
