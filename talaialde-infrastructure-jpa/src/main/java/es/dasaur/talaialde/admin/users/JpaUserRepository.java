package es.dasaur.talaialde.admin.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<JpaUser, Long> {
    
    JpaUser findByUsername(String username);

    List<JpaUser> findByActiveTrue();

}
