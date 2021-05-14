package es.dasaur.talaialde.billing.freebill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;
import es.dasaur.talaialde.billing.freeline.FreeBillLinePresenter;
import es.dasaur.talaialde.billing.freeline.JpaFreeLine;
import es.dasaur.talaialde.management.clients.JpaClient;
import es.dasaur.talaialde.utils.pdf.PdfUtils;
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
