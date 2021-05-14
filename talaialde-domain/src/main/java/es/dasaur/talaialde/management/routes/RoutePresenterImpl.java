package es.dasaur.talaialde.management.routes;

import java.util.List;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;

@Presenter
public class RoutePresenterImpl 
        extends AbstractPresenter<RouteService, RouteView>
        implements RoutePresenter {

    @Override
    public List<JpaRoute> findAllRoutes() {
        return model().findAllRoutes();
    }
    
    @Override
    public JpaRoute saveRoute(JpaRoute c) {
        return model().saveRoute(c);
    }
    
    @Override
    public void removeRoute(JpaRoute c) {
        model().removeRoute(c);
    }

}
