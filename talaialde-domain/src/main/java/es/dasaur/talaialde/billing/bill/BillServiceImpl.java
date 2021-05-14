package es.dasaur.talaialde.billing.bill;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.dasaur.talaialde.billing.line.JpaLine;
import es.dasaur.talaialde.billing.line.JpaLineRepository;
import es.dasaur.talaialde.billing.vat.JpaVatRepository;
import es.dasaur.talaialde.management.clients.JpaClient;
import es.dasaur.talaialde.management.clients.JpaClientRepository;
import es.dasaur.talaialde.management.routes.JpaRoute;
import es.dasaur.talaialde.management.routes.JpaRouteRepository;
import es.dasaur.talaialde.management.tractors.JpaTractor;
import es.dasaur.talaialde.management.tractors.JpaTractorRepository;

@Service
@Transactional(readOnly = true)
public class BillServiceImpl 
        implements BillService{
    
    @Autowired
    private JpaLineRepository repo;

    @Autowired
    private JpaClientRepository clientRepo;
    @Autowired
    private JpaRouteRepository routeRepo;
    @Autowired
    private JpaTractorRepository tractorRepo;
    
    @Autowired
    private JpaVatRepository vatRepo;
    
    @Override
    public List<JpaLine> getLines(JpaClient client, JpaRoute route, JpaTractor tractor,
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
        
        List<JpaLine> lines = repo.findByFilters(
                client, route, tractor, beginDate, finishDate);
        lines.forEach(line -> line.setChecked(true));
        
        return lines;
    }

    @Override
    public List<JpaClient> getClients() {
        return clientRepo.findByDeletedFalse();
    }

    @Override
    public List<JpaRoute> getRoutes() {
        return routeRepo.findByDeletedFalse();
    }

    @Override
    public List<JpaTractor> getTractors() {
        return tractorRepo.findByDeletedFalse();
    }

    @Override
    public BigDecimal getDefaultVat() {
        return vatRepo.findOne(1L).getValue();
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteLine(JpaLine line) {
        repo.delete(line);
    }

}
