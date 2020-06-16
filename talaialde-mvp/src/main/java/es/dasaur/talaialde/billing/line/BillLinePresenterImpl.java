package es.dasaur.talaialde.billing.line;

import java.util.List;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;
import es.dasaur.talaialde.billing.line.Line;
import es.dasaur.talaialde.management.clients.Client;
import es.dasaur.talaialde.management.routes.Route;
import es.dasaur.talaialde.management.tractors.Tractor;

@Presenter
public class BillLinePresenterImpl 
        extends AbstractPresenter<BillLineService, BillLineView>
        implements BillLinePresenter {
    
    @Override
    public Line saveLine(Line c) {
        boolean isNew = c.getId() == null;
        Line saved = model().saveLine(c);
        if(!isNew) {
            getMainPresenter().view().closeView(getTitle());
        }
        return saved;
    }

    @Override
    public List<Client> getClients() {
        return model().getClients();
    }

    @Override
    public List<Route> getRoutes() {
        return model().getRoutes();
    }

    @Override
    public List<Tractor> getTractors() {
        return model().getTractors();
    }

    @Override
    public void setLine(Line line) {
        view().setLine(line);
    }

}
