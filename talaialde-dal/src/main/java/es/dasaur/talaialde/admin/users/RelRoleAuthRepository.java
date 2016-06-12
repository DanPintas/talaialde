package es.dasaur.talaialde.admin.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RelRoleAuthRepository 
        extends JpaRepository<RelRoleAuth, Long> {

    List<RelRoleAuth> findByRole(Role role);

}
