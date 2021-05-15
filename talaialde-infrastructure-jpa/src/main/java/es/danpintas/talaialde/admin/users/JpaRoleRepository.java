package es.danpintas.talaialde.admin.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRoleRepository
        extends JpaRepository<JpaRole, Long>{

    List<JpaRole> findByOrderById();

}
