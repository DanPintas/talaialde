package es.dasaur.talaialde.billing.line;

import java.util.List;

import es.dasaur.talaialde.management.clients.JpaClient;
import es.dasaur.talaialde.management.routes.JpaRoute;
import es.dasaur.talaialde.management.tractors.JpaTractor;

public interface BillLineService {

    JpaLine saveLine(JpaLine c);

    List<JpaClient> getClients();

    List<JpaRoute> getRoutes();

    List<JpaTractor> getTractors();

}
