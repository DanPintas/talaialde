package es.danpintas.talaialde.billing.freebill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.danpintas.mvp.Presenter;
import es.danpintas.talaialde.billing.freeline.JpaFreeLine;
import es.danpintas.talaialde.management.clients.JpaClient;
import net.sf.jasperreports.engine.JRException;

public interface FreeBillPresenter 
        extends Presenter<FreeBillService, FreeBillView> {

    List<JpaFreeLine> getLines(JpaClient client, Date startDate, Date endDate);

    List<JpaClient> getClients();
    
    BigDecimal getDefaultVat();
    
    byte[] getPdf(JpaClient client, Date startDate, Date endDate,
                  List<JpaFreeLine> lines, String vat, String totalWithCurrency,
                  String logoPath, String billN, Date date)
                    throws JRException;

    void deleteLine(JpaFreeLine line);

    void openEditor(JpaFreeLine line);

}
