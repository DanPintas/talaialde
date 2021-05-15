package es.danpintas.talaialde.management.clients;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl 
        implements ClientService{
    
    @Autowired
    private JpaClientRepository repo;
    
    @Override
    public List<JpaClient> findAllClients() {
        return repo.findByDeletedFalse();
    }
    
    @Override
    @Transactional(readOnly = false)
    public JpaClient saveClient(JpaClient c) {
        return repo.save(c);
    }

    @Override
    @Transactional(readOnly = false)
    public void removeClient(JpaClient c) {
        c.setDeleted(true);
        repo.save(c);
    }

}
