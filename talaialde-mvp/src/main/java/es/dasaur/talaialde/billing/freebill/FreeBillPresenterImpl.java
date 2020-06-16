package es.dasaur.talaialde.billing.freebill;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;
import es.dasaur.talaialde.billing.freeline.FreeBillLinePresenter;
import es.dasaur.talaialde.billing.freeline.FreeLine;
import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.utils.pdf.PdfUtils;
import net.sf.jasperreports.engine.JRException;

@Presenter
public class FreeBillPresenterImpl 
        extends AbstractPresenter<FreeBillService, FreeBillView>
        implements FreeBillPresenter {

    @Override
    public List<FreeLine> getLines(Client client, Date startDate, Date endDate) {
        return model().getLines(client, startDate, endDate);
    }

    @Override
    public List<Client> getClients() {
        return model().getClients();
    }

    @Override
    public BigDecimal getDefaultVat() {
        return model().getDefaultVat();
    }

    @Override
    public byte[] getPdf(Client client, Date startDate, Date endDate, 
            List<FreeLine> lines, String vat, String totalWithCurrency, 
            String logoPath, String billN, Date date) 
                    throws JRException {
        return PdfUtils.getFreeBill(client, startDate, endDate,
                lines, vat, totalWithCurrency, logoPath, billN, date);
    }

    @Override
    public void deleteLine(FreeLine line) {
        model().deleteLine(line);
    }

    @Override
    public void openEditor(FreeLine line) {
        FreeBillLinePresenter p = (FreeBillLinePresenter) getMainPresenter()
                .openPresenter(FreeBillLinePresenter.class, "Editar línea (libre)");
        p.setLine(line);
    }

}
