package es.dasaur.talaialde.admin.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository 
        extends JpaRepository<Role, Long>{

    List<Role> findByOrderById();

}
