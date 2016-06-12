package es.dasaur.talaialde.admin.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository 
        extends JpaRepository<Auth, Long> {

    List<Auth> findByOrderById();

}
