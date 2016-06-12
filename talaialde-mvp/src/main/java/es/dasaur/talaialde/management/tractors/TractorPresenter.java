package es.dasaur.talaialde.management.tractors;

import java.util.List;

import es.dasaur.mvp.Presenter;

public interface TractorPresenter 
        extends Presenter<TractorService, TractorView> {

    List<Tractor> findAllTractors();

    Tractor saveTractor(Tractor c);

    void removeTractor(Tractor c);

}
