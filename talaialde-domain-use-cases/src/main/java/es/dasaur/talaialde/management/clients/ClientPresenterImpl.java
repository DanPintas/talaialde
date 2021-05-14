package es.dasaur.talaialde.management.clients;

import java.util.List;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;

@Presenter
public class ClientPresenterImpl 
        extends AbstractPresenter<ClientService, ClientView>
        implements ClientPresenter {

    @Override
    public List<JpaClient> findAllClients() {
        return model().findAllClients();
    }
    
    @Override
    public JpaClient saveClient(JpaClient c) {
        return model().saveClient(c);
    }
    
    @Override
    public void removeClient(JpaClient c) {
        model().removeClient(c);
    }

}
