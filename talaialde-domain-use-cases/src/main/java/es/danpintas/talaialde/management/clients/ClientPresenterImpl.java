package es.danpintas.talaialde.management.clients;

import es.danpintas.annotations.Presenter;
import es.danpintas.mvp.AbstractPresenter;
import java.util.List;

@Presenter
public class ClientPresenterImpl
    extends AbstractPresenter<ClientService, ClientView>
    implements ClientPresenter {

  @Override
  public List<JpaClient> findAllClients() {
    return model().findAllClients();
  }

  @Override
  public JpaClient saveClient(JpaClient c) {
    return model().saveClient(c);
  }

  @Override
  public void removeClient(JpaClient c) {
    model().removeClient(c);
  }

}
