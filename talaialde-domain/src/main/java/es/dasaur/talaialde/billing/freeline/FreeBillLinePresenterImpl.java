package es.dasaur.talaialde.billing.freeline;

import java.util.List;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;
import es.dasaur.talaialde.management.clients.JpaClient;

@Presenter
public class FreeBillLinePresenterImpl 
        extends AbstractPresenter<FreeBillLineService, FreeBillLineView>
        implements FreeBillLinePresenter {
    
    @Override
    public JpaFreeLine saveLine(JpaFreeLine c) {
        boolean isNew = c.getId() == null;
        JpaFreeLine saved = model().saveLine(c);
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
    public void setLine(JpaFreeLine line) {
        view().setLine(line);
    }

}
