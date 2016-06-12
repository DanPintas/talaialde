package es.dasaur.talaialde.management.routes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository 
        extends JpaRepository<Route, Long>{

    List<Route> findByDeletedFalse();

}
