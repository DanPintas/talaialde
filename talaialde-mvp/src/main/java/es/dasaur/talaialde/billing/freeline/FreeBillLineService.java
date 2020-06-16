package es.dasaur.talaialde.billing.freeline;

import java.util.List;

import es.dasaur.talaialde.management.clients.Client;

public interface FreeBillLineService {

    FreeLine saveLine(FreeLine c);

    List<Client> getClients();

}
