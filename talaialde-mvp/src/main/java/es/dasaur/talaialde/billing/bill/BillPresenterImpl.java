package es.dasaur.talaialde.billing.bill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;
import es.dasaur.talaialde.billing.line.BillLinePresenter;
import es.dasaur.talaialde.billing.line.Line;
import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.management.routes.Route;
import es.dasaur.talaialde.management.tractors.Tractor;
import es.dasaur.talaialde.utils.pdf.PdfUtils;
import net.sf.jasperreports.engine.JRException;

@Presenter
public class BillPresenterImpl 
        extends AbstractPresenter<BillService, BillView>
        implements BillPresenter {

    @Override
    public List<Line> getLines(Client client, Route route, Tractor tractor,
            Date startDate, Date endDate) {
        return model().getLines(client, route, tractor, startDate, endDate);
    }

    @Override
    public List<Client> getClients() {
        return model().getClients();
    }

    @Override
    public List<Route> getRoutes() {
        return model().getRoutes();
    }

    @Override
    public List<Tractor> getTractors() {
        return model().getTractors();
    }

    @Override
    public BigDecimal getDefaultVat() {
        return model().getDefaultVat();
    }

    @Override
    public byte[] getPdf(Client client, Route route, Tractor tractor,
            Date startDate, Date endDate, List<Line> lines, String vat,
            String totalWithCurrency, String logoPath, String billN,
            Date date) 
                    throws JRException {
        return PdfUtils.getBill(client, route, tractor, startDate, endDate,
                lines, vat, totalWithCurrency, logoPath, billN, date);
    }

    @Override
    public void deleteLine(Line line) {
        model().deleteLine(line);
    }

    @Override
    public void openEditor(Line line) {
        BillLinePresenter p = (BillLinePresenter) getMainPresenter()
                .openPresenter(BillLinePresenter.class, "Editar línea");
        p.setLine(line);
    }

}
