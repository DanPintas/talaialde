package es.dasaur.talaialde.billing.freebill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.dasaur.talaialde.billing.freeline.FreeLine;
import es.dasaur.talaialde.management.clients.Client;

public interface FreeBillService {

    List<FreeLine> getLines(Client client, Date startDate, Date endDate);

    List<Client> getClients();

    BigDecimal getDefaultVat();

    void deleteLine(FreeLine line);

}
