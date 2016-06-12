package es.dasaur.talaialde.billing.bill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.dasaur.talaialde.billing.line.Line;
import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.management.routes.Route;
import es.dasaur.talaialde.management.tractors.Tractor;

public interface BillService {

    List<Line> getLines(Client client, Route route, Tractor tractor,
            Date startDate, Date endDate);

    List<Client> getClients();

    List<Route> getRoutes();

    List<Tractor> getTractors();

    BigDecimal getDefaultVat();

}
