package es.dasaur.talaialde.management.clients;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository 
        extends JpaRepository<Client, Long>{

    List<Client> findByDeletedFalse();

}
