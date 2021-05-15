package es.danpintas.talaialde.admin.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAuthRepository
        extends JpaRepository<JpaAuth, Long> {

    List<JpaAuth> findByOrderById();

}
