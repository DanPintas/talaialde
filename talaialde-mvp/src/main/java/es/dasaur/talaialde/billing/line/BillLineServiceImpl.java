package es.dasaur.talaialde.billing.line;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.dasaur.talaialde.billing.line.Line;
import es.dasaur.talaialde.billing.line.LineRepository;
import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.management.clients.ClientRepository;
import es.dasaur.talaialde.management.routes.Route;
import es.dasaur.talaialde.management.routes.RouteRepository;
import es.dasaur.talaialde.management.tractors.Tractor;
import es.dasaur.talaialde.management.tractors.TractorRepository;

@Service
@Transactional(readOnly = true)
public class BillLineServiceImpl 
        implements BillLineService{
    
    @Autowired
    private LineRepository repo;
    
    @Autowired
    private ClientRepository clientRepo;
    @Autowired
    private RouteRepository routeRepo;
    @Autowired
    private TractorRepository tractorRepo;
    
    @Override
    @Transactional(readOnly = false)
    public Line saveLine(Line c) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(c.getLineDate());
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        c.setLineDate(cal.getTime());
        return repo.save(c);
    }

    @Override
    public List<Client> getClients() {
        return clientRepo.findByDeletedFalse();
    }

    @Override
    public List<Route> getRoutes() {
        return routeRepo.findByDeletedFalse();
    }

    @Override
    public List<Tractor> getTractors() {
        return tractorRepo.findByDeletedFalse();
    }

}
