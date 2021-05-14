package es.dasaur.talaialde.billing.freeline;

import java.util.List;

import es.dasaur.talaialde.management.clients.JpaClient;

public interface FreeBillLineService {

    JpaFreeLine saveLine(JpaFreeLine c);

    List<JpaClient> getClients();

}
