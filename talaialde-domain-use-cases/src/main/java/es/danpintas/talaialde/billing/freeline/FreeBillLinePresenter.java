package es.danpintas.talaialde.billing.freeline;

import es.danpintas.mvp.Presenter;
import es.danpintas.talaialde.management.clients.JpaClient;
import java.util.List;

public interface FreeBillLinePresenter
    extends Presenter<FreeBillLineService, FreeBillLineView> {

  JpaFreeLine saveLine(JpaFreeLine c);

  List<JpaClient> getClients();

  void setLine(JpaFreeLine line);

}
