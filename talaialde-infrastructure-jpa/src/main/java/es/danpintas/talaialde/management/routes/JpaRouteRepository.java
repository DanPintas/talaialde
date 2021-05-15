package es.danpintas.talaialde.management.routes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRouteRepository
        extends JpaRepository<JpaRoute, Long>{

    List<JpaRoute> findByDeletedFalse();

}
