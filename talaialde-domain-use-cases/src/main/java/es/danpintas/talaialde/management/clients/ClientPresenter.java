package es.danpintas.talaialde.management.clients;

import java.util.List;

import es.danpintas.mvp.Presenter;

public interface ClientPresenter 
        extends Presenter<ClientService, ClientView> {

    List<JpaClient> findAllClients();

    JpaClient saveClient(JpaClient c);

    void removeClient(JpaClient c);

}
