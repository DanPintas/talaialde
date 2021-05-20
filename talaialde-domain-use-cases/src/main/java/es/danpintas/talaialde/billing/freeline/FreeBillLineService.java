package es.danpintas.talaialde.billing.freeline;

import es.danpintas.talaialde.management.clients.JpaClient;
import java.util.List;

public interface FreeBillLineService {

  JpaFreeLine saveLine(JpaFreeLine c);

  List<JpaClient> getClients();

}
