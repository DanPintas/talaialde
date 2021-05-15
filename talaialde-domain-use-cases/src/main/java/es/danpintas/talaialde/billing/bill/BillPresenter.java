package es.danpintas.talaialde.billing.bill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.danpintas.mvp.Presenter;
import es.danpintas.talaialde.billing.line.JpaLine;
import es.danpintas.talaialde.management.clients.JpaClient;
import es.danpintas.talaialde.management.routes.JpaRoute;
import es.danpintas.talaialde.management.tractors.JpaTractor;
import net.sf.jasperreports.engine.JRException;

public interface BillPresenter 
        extends Presenter<BillService, BillView> {

    List<JpaLine> getLines(JpaClient client, JpaRoute route, JpaTractor tractor,
                           Date startDate, Date endDate);

    List<JpaClient> getClients();

    List<JpaRoute> getRoutes();

    List<JpaTractor> getTractors();

    BigDecimal getDefaultVat();
    
    byte[] getPdf(JpaClient client, JpaRoute route, JpaTractor tractor, Date startDate,
                  Date endDate, List<JpaLine> lines, String vat,
                  String totalWithCurrency, String logoPath, String billN, Date date)
                    throws JRException;

    void deleteLine(JpaLine line);

    void openEditor(JpaLine line);

}
