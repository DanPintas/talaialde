package es.danpintas.talaialde.billing.freebill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.danpintas.talaialde.billing.freeline.JpaFreeLine;
import es.danpintas.talaialde.management.clients.JpaClient;

public interface FreeBillService {

    List<JpaFreeLine> getLines(JpaClient client, Date startDate, Date endDate);

    List<JpaClient> getClients();

    BigDecimal getDefaultVat();

    void deleteLine(JpaFreeLine line);

}
