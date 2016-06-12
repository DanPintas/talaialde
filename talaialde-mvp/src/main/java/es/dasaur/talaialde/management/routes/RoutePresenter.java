package es.dasaur.talaialde.management.routes;

import java.util.List;

import es.dasaur.mvp.Presenter;

public interface RoutePresenter 
        extends Presenter<RouteService, RouteView> {

    List<Route> findAllRoutes();

    Route saveRoute(Route c);

    void removeRoute(Route c);

}
