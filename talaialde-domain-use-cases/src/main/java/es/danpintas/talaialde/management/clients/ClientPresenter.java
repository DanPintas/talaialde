package es.danpintas.talaialde.management.clients;

import es.danpintas.mvp.Presenter;
import java.util.List;

public interface ClientPresenter
    extends Presenter<ClientService, ClientView> {

  List<JpaClient> findAllClients();

  JpaClient saveClient(JpaClient c);

  void removeClient(JpaClient c);

}
