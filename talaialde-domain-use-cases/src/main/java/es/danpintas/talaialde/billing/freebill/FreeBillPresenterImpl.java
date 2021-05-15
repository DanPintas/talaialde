package es.danpintas.talaialde.billing.freebill;

import es.danpintas.talaialde.billing.freeline.FreeBillLinePresenter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.danpintas.annotations.Presenter;
import es.danpintas.mvp.AbstractPresenter;
import es.danpintas.talaialde.billing.freeline.JpaFreeLine;
import es.danpintas.talaialde.management.clients.JpaClient;
import es.danpintas.talaialde.utils.pdf.PdfUtils;
import net.sf.jasperreports.engine.JRException;

@Presenter
public class FreeBillPresenterImpl 
        extends AbstractPresenter<FreeBillService, FreeBillView>
        implements FreeBillPresenter {

    @Override
    public List<JpaFreeLine> getLines(JpaClient client, Date startDate, Date endDate) {
        return model().getLines(client, startDate, endDate);
    }

    @Override
    public List<JpaClient> getClients() {
        return model().getClients();
    }

    @Override
    public BigDecimal getDefaultVat() {
        return model().getDefaultVat();
    }

    @Override
    public byte[] getPdf(JpaClient client, Date startDate, Date endDate,
                         List<JpaFreeLine> lines, String vat, String totalWithCurrency,
                         String logoPath, String billN, Date date)
                    throws JRException {
        return PdfUtils.getFreeBill(client, startDate, endDate,
                lines, vat, totalWithCurrency, logoPath, billN, date);
    }

    @Override
    public void deleteLine(JpaFreeLine line) {
        model().deleteLine(line);
    }

    @Override
    public void openEditor(JpaFreeLine line) {
        FreeBillLinePresenter p = (FreeBillLinePresenter) getMainPresenter()
                .openPresenter(FreeBillLinePresenter.class, "Editar línea (libre)");
        p.setLine(line);
    }

}
