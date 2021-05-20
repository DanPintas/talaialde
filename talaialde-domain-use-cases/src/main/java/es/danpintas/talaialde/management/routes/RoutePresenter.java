package es.danpintas.talaialde.management.routes;

import es.danpintas.mvp.Presenter;
import java.util.List;

public interface RoutePresenter
    extends Presenter<RouteService, RouteView> {

  List<JpaRoute> findAllRoutes();

  JpaRoute saveRoute(JpaRoute c);

  void removeRoute(JpaRoute c);

}
