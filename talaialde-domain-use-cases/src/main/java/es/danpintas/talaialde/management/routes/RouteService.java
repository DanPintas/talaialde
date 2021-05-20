package es.danpintas.talaialde.management.routes;

import java.util.List;

public interface RouteService {

  List<JpaRoute> findAllRoutes();

  JpaRoute saveRoute(JpaRoute c);

  void removeRoute(JpaRoute c);


}
