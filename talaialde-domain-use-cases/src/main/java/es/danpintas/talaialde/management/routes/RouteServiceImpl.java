package es.danpintas.talaialde.management.routes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RouteServiceImpl 
        implements RouteService{
    
    @Autowired
    private JpaRouteRepository repo;
    
    @Override
    public List<JpaRoute> findAllRoutes() {
        return repo.findByDeletedFalse();
    }
    
    @Override
    @Transactional(readOnly = false)
    public JpaRoute saveRoute(JpaRoute c) {
        return repo.save(c);
    }

    @Override
    @Transactional(readOnly = false)
    public void removeRoute(JpaRoute c) {
        c.setDeleted(true);
        repo.save(c);
    }

}
