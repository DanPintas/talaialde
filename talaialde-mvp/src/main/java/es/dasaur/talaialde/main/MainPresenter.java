package es.dasaur.talaialde.main;

import es.dasaur.mvp.Presenter;

public interface MainPresenter extends Presenter<MainService, MainView> {

    Presenter<?, ?> openPresenter(Class<? extends Presenter<?, ?>> tipoAccion,
            String titulo);

    void closePresenter(String titulo);

    void logout();

}
