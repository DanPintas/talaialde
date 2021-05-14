package es.dasaur.talaialde.management.routes;

import java.util.List;

import es.dasaur.mvp.Presenter;

public interface RoutePresenter 
        extends Presenter<RouteService, RouteView> {

    List<JpaRoute> findAllRoutes();

    JpaRoute saveRoute(JpaRoute c);

    void removeRoute(JpaRoute c);

}
