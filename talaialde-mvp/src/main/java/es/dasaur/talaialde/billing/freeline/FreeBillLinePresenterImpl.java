package es.dasaur.talaialde.billing.freeline;

import java.util.List;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;
import es.dasaur.talaialde.management.clients.Client;

@Presenter
public class FreeBillLinePresenterImpl 
        extends AbstractPresenter<FreeBillLineService, FreeBillLineView>
        implements FreeBillLinePresenter {
    
    @Override
    public FreeLine saveLine(FreeLine c) {
        boolean isNew = c.getId() == null;
        FreeLine saved = model().saveLine(c);
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
    public void setLine(FreeLine line) {
        view().setLine(line);
    }

}
