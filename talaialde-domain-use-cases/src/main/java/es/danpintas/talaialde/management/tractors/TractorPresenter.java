package es.danpintas.talaialde.management.tractors;

import es.danpintas.mvp.Presenter;
import java.util.List;

public interface TractorPresenter
    extends Presenter<TractorService, TractorView> {

  List<JpaTractor> findAllTractors();

  JpaTractor saveTractor(JpaTractor c);

  void removeTractor(JpaTractor c);

}
