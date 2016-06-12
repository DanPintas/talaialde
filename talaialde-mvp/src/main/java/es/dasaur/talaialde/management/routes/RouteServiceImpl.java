package es.dasaur.talaialde.management.routes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class RouteServiceImpl 
        implements RouteService{
    
    @Autowired
    private RouteRepository repo;
    
    @Override
    public List<Route> findAllRoutes() {
        return repo.findByDeletedFalse();
    }
    
    @Override
    @Transactional(readOnly = false)
    public Route saveRoute(Route c) {
        return repo.save(c);
    }

    @Override
    @Transactional(readOnly = false)
    public void removeRoute(Route c) {
        c.setDeleted(true);
        repo.save(c);
    }

}
