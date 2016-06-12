package es.dasaur.talaialde.billing.bill;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.dasaur.talaialde.billing.line.Line;
import es.dasaur.talaialde.billing.line.LineRepository;
import es.dasaur.talaialde.billing.vat.VatRepository;
import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.management.clients.ClientRepository;
import es.dasaur.talaialde.management.routes.Route;
import es.dasaur.talaialde.management.routes.RouteRepository;
import es.dasaur.talaialde.management.tractors.Tractor;
import es.dasaur.talaialde.management.tractors.TractorRepository;

@Service
@Transactional(readOnly = true)
public class BillServiceImpl 
        implements BillService{
    
    @Autowired
    private LineRepository repo;

    @Autowired
    private ClientRepository clientRepo;
    @Autowired
    private RouteRepository routeRepo;
    @Autowired
    private TractorRepository tractorRepo;
    
    @Autowired
    private VatRepository vatRepo;
    
    @Override
    public List<Line> getLines(Client client, Route route, Tractor tractor,
            Date startDate, Date endDate) {
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(startDate);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMinimum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMinimum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMinimum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, cal.getActualMinimum(Calendar.MILLISECOND));
        Date beginDate = new java.sql.Date(cal.getTimeInMillis());
        
        cal.setTime(endDate);
        cal.set(Calendar.HOUR_OF_DAY, cal.getActualMaximum(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, cal.getActualMaximum(Calendar.MINUTE));
        cal.set(Calendar.SECOND, cal.getActualMaximum(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, 0);
        Date finishDate = new java.sql.Date(cal.getTimeInMillis());
        
        return repo.findByFilters(client, route, tractor, beginDate, finishDate);
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

    @Override
    public BigDecimal getDefaultVat() {
        return vatRepo.findOne(1L).getValue();
    }

}
