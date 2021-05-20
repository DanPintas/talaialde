package es.danpintas.talaialde.management.routes;

import es.danpintas.annotations.Presenter;
import es.danpintas.mvp.AbstractPresenter;
import java.util.List;

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
