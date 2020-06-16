package es.dasaur.talaialde.billing.freebill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.dasaur.mvp.Presenter;
import es.dasaur.talaialde.billing.freeline.FreeLine;
import es.dasaur.talaialde.management.clients.Client;
import net.sf.jasperreports.engine.JRException;

public interface FreeBillPresenter 
        extends Presenter<FreeBillService, FreeBillView> {

    List<FreeLine> getLines(Client client, Date startDate, Date endDate);

    List<Client> getClients();
    
    BigDecimal getDefaultVat();
    
    byte[] getPdf(Client client, Date startDate,Date endDate, 
            List<FreeLine> lines, String vat, String totalWithCurrency, 
            String logoPath, String billN, Date date)
                    throws JRException;

    void deleteLine(FreeLine line);

    void openEditor(FreeLine line);

}
