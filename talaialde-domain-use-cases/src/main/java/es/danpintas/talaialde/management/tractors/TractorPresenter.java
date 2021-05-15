package es.danpintas.talaialde.management.tractors;

import java.util.List;

import es.danpintas.mvp.Presenter;

public interface TractorPresenter 
        extends Presenter<TractorService, TractorView> {

    List<JpaTractor> findAllTractors();

    JpaTractor saveTractor(JpaTractor c);

    void removeTractor(JpaTractor c);

}
