package es.danpintas.talaialde.management.tractors;

import java.util.List;

import es.danpintas.annotations.Presenter;
import es.danpintas.mvp.AbstractPresenter;

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
