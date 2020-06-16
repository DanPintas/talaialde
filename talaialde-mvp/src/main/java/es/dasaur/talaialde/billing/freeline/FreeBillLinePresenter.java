package es.dasaur.talaialde.billing.freeline;

import java.util.List;

import es.dasaur.mvp.Presenter;
import es.dasaur.talaialde.management.clients.Client;

public interface FreeBillLinePresenter 
        extends Presenter<FreeBillLineService, FreeBillLineView> {

    FreeLine saveLine(FreeLine c);

    List<Client> getClients();

    void setLine(FreeLine line);

}
