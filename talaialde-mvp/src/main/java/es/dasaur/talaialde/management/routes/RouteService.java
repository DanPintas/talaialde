package es.dasaur.talaialde.management.routes;

import java.util.List;

public interface RouteService {

    List<Route> findAllRoutes();

    Route saveRoute(Route c);

    void removeRoute(Route c);
    
    

}
