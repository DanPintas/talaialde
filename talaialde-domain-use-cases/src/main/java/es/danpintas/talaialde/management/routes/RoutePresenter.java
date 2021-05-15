package es.danpintas.talaialde.management.routes;

import java.util.List;

import es.danpintas.mvp.Presenter;

public interface RoutePresenter 
        extends Presenter<RouteService, RouteView> {

    List<JpaRoute> findAllRoutes();

    JpaRoute saveRoute(JpaRoute c);

    void removeRoute(JpaRoute c);

}