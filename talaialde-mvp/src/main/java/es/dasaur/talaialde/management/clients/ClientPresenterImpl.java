package es.dasaur.talaialde.management.clients;

import java.util.List;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;
import es.dasaur.talaialde.management.clients.Client;

@Presenter
public class ClientPresenterImpl 
        extends AbstractPresenter<ClientService, ClientView>
        implements ClientPresenter {

    @Override
    public List<Client> findAllClients() {
        return model().findAllClients();
    }
    
    @Override
    public Client saveClient(Client c) {
        return model().saveClient(c);
    }
    
    @Override
    public void removeClient(Client c) {
        model().removeClient(c);
    }

}
