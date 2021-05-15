package es.danpintas.talaialde.management.tractors;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaTractorRepository
        extends JpaRepository<JpaTractor, Long>{

    List<JpaTractor> findByDeletedFalse();

}
