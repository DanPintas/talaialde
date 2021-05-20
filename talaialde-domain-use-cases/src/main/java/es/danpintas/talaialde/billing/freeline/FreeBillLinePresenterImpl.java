package es.danpintas.talaialde.billing.freeline;

import es.danpintas.annotations.Presenter;
import es.danpintas.mvp.AbstractPresenter;
import es.danpintas.talaialde.management.clients.JpaClient;
import java.util.List;

@Presenter
public class FreeBillLinePresenterImpl
    extends AbstractPresenter<FreeBillLineService, FreeBillLineView>
    implements FreeBillLinePresenter {

  @Override
  public JpaFreeLine saveLine(JpaFreeLine c) {
    boolean isNew = c.getId() == null;
    JpaFreeLine saved = model().saveLine(c);
    if (!isNew) {
      getMainPresenter().view().closeView(getTitle());
    }
    return saved;
  }

  @Override
  public List<JpaClient> getClients() {
    return model().getClients();
  }

  @Override
  public void setLine(JpaFreeLine line) {
    view().setLine(line);
  }

}
