package es.danpintas.talaialde.admin.users;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRelRoleAuthRepository
    extends JpaRepository<JpaRelRoleAuth, Long> {

  List<JpaRelRoleAuth> findByRole(JpaRole role);

}
