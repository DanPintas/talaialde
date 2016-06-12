package es.dasaur.talaialde.management.tractors;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TractorRepository 
        extends JpaRepository<Tractor, Long>{

    List<Tractor> findByDeletedFalse();

}
