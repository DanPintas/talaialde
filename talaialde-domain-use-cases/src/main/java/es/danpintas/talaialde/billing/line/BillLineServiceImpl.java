package es.danpintas.talaialde.billing.line;

import es.danpintas.talaialde.management.clients.JpaClient;
import es.danpintas.talaialde.management.clients.JpaClientRepository;
import es.danpintas.talaialde.management.routes.JpaRoute;
import es.danpintas.talaialde.management.routes.JpaRouteRepository;
import es.danpintas.talaialde.management.tractors.JpaTractor;
import es.danpintas.talaialde.management.tractors.JpaTractorRepository;
import java.util.Calendar;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BillLineServiceImpl
    implements BillLineService {

  @Autowired
  private JpaLineRepository repo;

  @Autowired
  private JpaClientRepository clientRepo;

  @Autowired
  private JpaRouteRepository routeRepo;

  @Autowired
  private JpaTractorRepository tractorRepo;

  @Override
  @Transactional(readOnly = false)
  public JpaLine saveLine(JpaLine c) {
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

  @Override
  public List<JpaRoute> getRoutes() {
    return routeRepo.findByDeletedFalse();
  }

  @Override
  public List<JpaTractor> getTractors() {
    return tractorRepo.findByDeletedFalse();
  }

}
