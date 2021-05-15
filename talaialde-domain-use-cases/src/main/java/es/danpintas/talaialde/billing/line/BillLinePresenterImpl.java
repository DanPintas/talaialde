package es.danpintas.talaialde.billing.line;

import java.util.List;

import es.danpintas.annotations.Presenter;
import es.danpintas.mvp.AbstractPresenter;
import es.danpintas.talaialde.management.clients.JpaClient;
import es.danpintas.talaialde.management.routes.JpaRoute;
import es.danpintas.talaialde.management.tractors.JpaTractor;

@Presenter
public class BillLinePresenterImpl 
        extends AbstractPresenter<BillLineService, BillLineView>
        implements BillLinePresenter {
    
    @Override
    public JpaLine saveLine(JpaLine c) {
        boolean isNew = c.getId() == null;
        JpaLine saved = model().saveLine(c);
        if(!isNew) {
            getMainPresenter().view().closeView(getTitle());
        }
        return saved;
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
    public void setLine(JpaLine line) {
        view().setLine(line);
    }

}
