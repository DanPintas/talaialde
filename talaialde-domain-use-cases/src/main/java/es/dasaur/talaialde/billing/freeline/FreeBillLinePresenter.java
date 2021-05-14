package es.dasaur.talaialde.billing.freeline;

import java.util.List;

import es.dasaur.mvp.Presenter;
import es.dasaur.talaialde.management.clients.JpaClient;

public interface FreeBillLinePresenter 
        extends Presenter<FreeBillLineService, FreeBillLineView> {

    JpaFreeLine saveLine(JpaFreeLine c);

    List<JpaClient> getClients();

    void setLine(JpaFreeLine line);

}
