package es.dasaur.talaialde.management.clients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.management.clients.ClientRepository;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl 
        implements ClientService{
    
    @Autowired
    private ClientRepository repo;
    
    @Override
    public List<Client> findAllClients() {
        return repo.findByDeletedFalse();
    }
    
    @Override
    @Transactional(readOnly = false)
    public Client saveClient(Client c) {
        return repo.save(c);
    }

    @Override
    @Transactional(readOnly = false)
    public void removeClient(Client c) {
        c.setDeleted(true);
        repo.save(c);
    }

}
