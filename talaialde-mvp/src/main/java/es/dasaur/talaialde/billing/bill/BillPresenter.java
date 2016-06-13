package es.dasaur.talaialde.billing.bill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.dasaur.mvp.Presenter;
import es.dasaur.talaialde.billing.line.Line;
import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.management.routes.Route;
import es.dasaur.talaialde.management.tractors.Tractor;
import net.sf.jasperreports.engine.JRException;

public interface BillPresenter 
        extends Presenter<BillService, BillView> {

    List<Line> getLines(Client client, Route route, Tractor tractor, 
            Date startDate, Date endDate);

    List<Client> getClients();

    List<Route> getRoutes();

    List<Tractor> getTractors();

    BigDecimal getDefaultVat();
    
    byte[] getPdf(Client client, Route route, Tractor tractor, Date startDate,
            Date endDate, List<Line> lines, String vat,
            String totalWithCurrency, String logoPath, String billN, Date date)
                    throws JRException;

}
