package es.danpintas.talaialde.management.clients;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClientRepository
        extends JpaRepository<JpaClient, Long>{

    List<JpaClient> findByDeletedFalse();

}
