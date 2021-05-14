package es.dasaur.talaialde.billing.line;

import java.util.List;

import es.dasaur.mvp.Presenter;
import es.dasaur.talaialde.management.clients.JpaClient;
import es.dasaur.talaialde.management.routes.JpaRoute;
import es.dasaur.talaialde.management.tractors.JpaTractor;

public interface BillLinePresenter 
        extends Presenter<BillLineService, BillLineView> {

    JpaLine saveLine(JpaLine c);

    List<JpaClient> getClients();

    List<JpaRoute> getRoutes();

    List<JpaTractor> getTractors();

    void setLine(JpaLine line);

}
