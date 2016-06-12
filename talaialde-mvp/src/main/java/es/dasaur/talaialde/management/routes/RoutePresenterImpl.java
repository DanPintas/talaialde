package es.dasaur.talaialde.management.routes;

import java.util.List;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;

@Presenter
public class RoutePresenterImpl 
        extends AbstractPresenter<RouteService, RouteView>
        implements RoutePresenter {

    @Override
    public List<Route> findAllRoutes() {
        return model().findAllRoutes();
    }
    
    @Override
    public Route saveRoute(Route c) {
        return model().saveRoute(c);
    }
    
    @Override
    public void removeRoute(Route c) {
        model().removeRoute(c);
    }

}
