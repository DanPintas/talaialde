package es.danpintas.talaialde.billing.bill;

import es.danpintas.talaialde.utils.pdf.PdfUtils;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.danpintas.annotations.Presenter;
import es.danpintas.mvp.AbstractPresenter;
import es.danpintas.talaialde.billing.line.BillLinePresenter;
import es.danpintas.talaialde.billing.line.JpaLine;
import es.danpintas.talaialde.management.clients.JpaClient;
import es.danpintas.talaialde.management.routes.JpaRoute;
import es.danpintas.talaialde.management.tractors.JpaTractor;
import net.sf.jasperreports.engine.JRException;

@Presenter
public class BillPresenterImpl 
        extends AbstractPresenter<BillService, BillView>
        implements BillPresenter {

    @Override
    public List<JpaLine> getLines(JpaClient client, JpaRoute route, JpaTractor tractor,
                                  Date startDate, Date endDate) {
        return model().getLines(client, route, tractor, startDate, endDate);
    }

    @Override
    public List<JpaClient> getClients() {
        return model().getClients();
    }

    @Override
    public List<JpaRoute> getRoutes() {
        return model().getRoutes();
    }

    @Override
    public List<JpaTractor> getTractors() {
        return model().getTractors();
    }

    @Override
    public BigDecimal getDefaultVat() {
        return model().getDefaultVat();
    }

    @Override
    public byte[] getPdf(JpaClient client, JpaRoute route, JpaTractor tractor,
                         Date startDate, Date endDate, List<JpaLine> lines, String vat,
                         String totalWithCurrency, String logoPath, String billN,
                         Date date)
                    throws JRException {
        return PdfUtils.getBill(client, route, tractor, startDate, endDate,
                lines, vat, totalWithCurrency, logoPath, billN, date);
    }

    @Override
    public void deleteLine(JpaLine line) {
        model().deleteLine(line);
    }

    @Override
    public void openEditor(JpaLine line) {
        BillLinePresenter p = (BillLinePresenter) getMainPresenter()
                .openPresenter(BillLinePresenter.class, "Editar línea");
        p.setLine(line);
    }

}
