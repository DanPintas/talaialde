package es.dasaur.talaialde.billing.freebill;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.dasaur.talaialde.billing.vat.JpaVat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.dasaur.talaialde.billing.freeline.JpaFreeLine;
import es.dasaur.talaialde.billing.freeline.JpaFreeLineRepository;
import es.dasaur.talaialde.billing.vat.JpaVatRepository;
import es.dasaur.talaialde.management.clients.JpaClient;
import es.dasaur.talaialde.management.clients.JpaClientRepository;

@Service
@Transactional(readOnly = true)
public class FreeBillServiceImpl 
        implements FreeBillService{
    
    @Autowired
    private JpaFreeLineRepository repo;

    @Autowired
    private JpaClientRepository clientRepo;
    
    @Autowired
    private JpaVatRepository vatRepo;
    
    @Override
    public List<JpaFreeLine> getLines(JpaClient client, Date startDate, Date endDate) {
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
        
        List<JpaFreeLine> lines = repo.findByFilters(
                client, beginDate, finishDate);
        lines.forEach(line -> line.setChecked(true));
        
        return lines;
    }

    @Override
    public List<JpaClient> getClients() {
        return clientRepo.findByDeletedFalse();
    }

    @Override
    public BigDecimal getDefaultVat() {
        return vatRepo.findById(1L)
                .map(JpaVat::getValue)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteLine(JpaFreeLine line) {
        repo.delete(line);
    }

}
