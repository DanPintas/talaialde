package es.dasaur.talaialde.management.clients;

import java.util.List;

import es.dasaur.talaialde.management.clients.Client;

public interface ClientService {

    List<Client> findAllClients();

    Client saveClient(Client c);

    void removeClient(Client c);
    
    

}
