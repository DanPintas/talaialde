package es.dasaur.talaialde.billing.line;

import java.util.List;

import es.dasaur.talaialde.billing.line.Line;
import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.management.routes.Route;
import es.dasaur.talaialde.management.tractors.Tractor;

public interface BillLineService {

    Line saveLine(Line c);

    List<Client> getClients();

    List<Route> getRoutes();

    List<Tractor> getTractors();

}
