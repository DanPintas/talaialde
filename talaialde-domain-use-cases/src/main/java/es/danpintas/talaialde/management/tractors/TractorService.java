package es.danpintas.talaialde.management.tractors;

import java.util.List;

public interface TractorService {

  List<JpaTractor> findAllTractors();

  JpaTractor saveTractor(JpaTractor c);

  void removeTractor(JpaTractor c);


}
