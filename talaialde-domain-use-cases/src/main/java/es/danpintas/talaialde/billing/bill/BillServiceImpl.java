package es.danpintas.talaialde.billing.bill;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import es.danpintas.talaialde.billing.vat.JpaVat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.danpintas.talaialde.billing.line.JpaLine;
import es.danpintas.talaialde.billing.line.JpaLineRepository;
import es.danpintas.talaialde.billing.vat.JpaVatRepository;
import es.danpintas.talaialde.management.clients.JpaClient;
import es.danpintas.talaialde.management.clients.JpaClientRepository;
import es.danpintas.talaialde.management.routes.JpaRoute;
import es.danpintas.talaialde.management.routes.JpaRouteRepository;
import es.danpintas.talaialde.management.tractors.JpaTractor;
import es.danpintas.talaialde.management.tractors.JpaTractorRepository;

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
        return vatRepo.findById(1L)
                .map(JpaVat::getValue)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteLine(JpaLine line) {
        repo.delete(line);
    }

}
