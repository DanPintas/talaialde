package es.dasaur.talaialde.management.clients;

import java.util.List;

import es.dasaur.mvp.Presenter;
import es.dasaur.talaialde.management.clients.Client;

public interface ClientPresenter 
        extends Presenter<ClientService, ClientView> {

    List<Client> findAllClients();

    Client saveClient(Client c);

    void removeClient(Client c);

}
