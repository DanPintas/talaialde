package es.danpintas.talaialde.billing.freeline;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.danpintas.talaialde.management.clients.JpaClient;
import es.danpintas.talaialde.management.clients.JpaClientRepository;

@Service
@Transactional(readOnly = true)
public class FreeBillLineServiceImpl 
        implements FreeBillLineService{
    
    @Autowired
    private JpaFreeLineRepository repo;
    
    @Autowired
    private JpaClientRepository clientRepo;
    
    @Override
    @Transactional(readOnly = false)
    public JpaFreeLine saveLine(JpaFreeLine c) {
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
    public List<JpaClient> getClients() {
        return clientRepo.findByDeletedFalse();
    }

}
