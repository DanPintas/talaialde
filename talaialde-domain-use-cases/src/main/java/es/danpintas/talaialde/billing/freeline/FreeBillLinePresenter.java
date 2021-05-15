package es.danpintas.talaialde.billing.freeline;

import java.util.List;

import es.danpintas.mvp.Presenter;
import es.danpintas.talaialde.management.clients.JpaClient;

public interface FreeBillLinePresenter 
        extends Presenter<FreeBillLineService, FreeBillLineView> {

    JpaFreeLine saveLine(JpaFreeLine c);

    List<JpaClient> getClients();

    void setLine(JpaFreeLine line);

}
