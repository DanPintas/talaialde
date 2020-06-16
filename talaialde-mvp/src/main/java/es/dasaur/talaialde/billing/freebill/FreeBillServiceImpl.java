package es.dasaur.talaialde.billing.freebill;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.dasaur.talaialde.billing.freeline.FreeLine;
import es.dasaur.talaialde.billing.freeline.FreeLineRepository;
import es.dasaur.talaialde.billing.vat.VatRepository;
import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.management.clients.ClientRepository;

@Service
@Transactional(readOnly = true)
public class FreeBillServiceImpl 
        implements FreeBillService{
    
    @Autowired
    private FreeLineRepository repo;

    @Autowired
    private ClientRepository clientRepo;
    
    @Autowired
    private VatRepository vatRepo;
    
    @Override
    public List<FreeLine> getLines(Client client, Date startDate, Date endDate) {
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
        
        List<FreeLine> lines = repo.findByFilters(
                client, beginDate, finishDate);
        lines.forEach(line -> line.setChecked(true));
        
        return lines;
    }

    @Override
    public List<Client> getClients() {
        return clientRepo.findByDeletedFalse();
    }

    @Override
    public BigDecimal getDefaultVat() {
        return vatRepo.findOne(1L).getValue();
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteLine(FreeLine line) {
        repo.delete(line);
    }

}
