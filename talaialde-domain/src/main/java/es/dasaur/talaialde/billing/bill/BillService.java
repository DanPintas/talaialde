package es.dasaur.talaialde.billing.bill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.dasaur.talaialde.billing.line.JpaLine;
import es.dasaur.talaialde.management.clients.JpaClient;
import es.dasaur.talaialde.management.routes.JpaRoute;
import es.dasaur.talaialde.management.tractors.JpaTractor;

public interface BillService {

    List<JpaLine> getLines(JpaClient client, JpaRoute route, JpaTractor tractor,
                           Date startDate, Date endDate);

    List<JpaClient> getClients();

    List<JpaRoute> getRoutes();

    List<JpaTractor> getTractors();

    BigDecimal getDefaultVat();

    void deleteLine(JpaLine line);

}
