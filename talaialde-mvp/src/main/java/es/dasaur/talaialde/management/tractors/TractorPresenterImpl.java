package es.dasaur.talaialde.management.tractors;

import java.util.List;

import es.dasaur.annotations.Presenter;
import es.dasaur.mvp.AbstractPresenter;

@Presenter
public class TractorPresenterImpl 
        extends AbstractPresenter<TractorService, TractorView>
        implements TractorPresenter {
    
    @Override
    public void init() {
        super.init();
    }

    @Override
    public List<Tractor> findAllTractors() {
        return model().findAllTractors();
    }
    
    @Override
    public Tractor saveTractor(Tractor c) {
        return model().saveTractor(c);
    }
    
    @Override
    public void removeTractor(Tractor c) {
        model().removeTractor(c);
    }

}
