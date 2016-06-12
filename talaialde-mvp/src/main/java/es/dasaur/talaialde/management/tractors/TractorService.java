package es.dasaur.talaialde.management.tractors;

import java.util.List;

public interface TractorService {

    List<Tractor> findAllTractors();

    Tractor saveTractor(Tractor c);

    void removeTractor(Tractor c);
    
    

}
