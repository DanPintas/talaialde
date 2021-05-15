package es.danpintas.talaialde.management.clients;

import java.util.List;

public interface ClientService {

    List<JpaClient> findAllClients();

    JpaClient saveClient(JpaClient c);

    void removeClient(JpaClient c);
    
    

}
