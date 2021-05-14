package es.dasaur.talaialde.management.tractors;

import java.util.List;

import es.dasaur.mvp.Presenter;

public interface TractorPresenter 
        extends Presenter<TractorService, TractorView> {

    List<JpaTractor> findAllTractors();

    JpaTractor saveTractor(JpaTractor c);

    void removeTractor(JpaTractor c);

}
