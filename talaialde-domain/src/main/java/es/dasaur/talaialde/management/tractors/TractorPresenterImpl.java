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
    public List<JpaTractor> findAllTractors() {
        return model().findAllTractors();
    }
    
    @Override
    public JpaTractor saveTractor(JpaTractor c) {
        return model().saveTractor(c);
    }
    
    @Override
    public void removeTractor(JpaTractor c) {
        model().removeTractor(c);
    }

}
