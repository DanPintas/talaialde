package es.dasaur.talaialde.billing.freebill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.dasaur.talaialde.billing.freeline.JpaFreeLine;
import es.dasaur.talaialde.management.clients.JpaClient;

public interface FreeBillService {

    List<JpaFreeLine> getLines(JpaClient client, Date startDate, Date endDate);

    List<JpaClient> getClients();

    BigDecimal getDefaultVat();

    void deleteLine(JpaFreeLine line);

}
