package es.dasaur.talaialde.billing.line;

import java.util.List;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;
import es.dasaur.talaialde.management.clients.JpaClient;
import es.dasaur.talaialde.management.routes.JpaRoute;
import es.dasaur.talaialde.management.tractors.JpaTractor;

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
