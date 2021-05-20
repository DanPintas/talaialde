package es.danpintas.talaialde.billing.line;

import es.danpintas.talaialde.management.clients.JpaClient;
import es.danpintas.talaialde.management.routes.JpaRoute;
import es.danpintas.talaialde.management.tractors.JpaTractor;
import java.util.List;

public interface BillLineService {

  JpaLine saveLine(JpaLine c);

  List<JpaClient> getClients();

  List<JpaRoute> getRoutes();

  List<JpaTractor> getTractors();

}
