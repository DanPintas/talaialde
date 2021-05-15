package es.danpintas.talaialde.billing.freeline;

import java.util.List;

import es.danpintas.talaialde.management.clients.JpaClient;

public interface FreeBillLineService {

    JpaFreeLine saveLine(JpaFreeLine c);

    List<JpaClient> getClients();

}
