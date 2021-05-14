package es.dasaur.talaialde.admin.users;

import java.util.List;

public interface UserService {

    List<JpaUser> findAllUsers();
    
    JpaUser saveUser(JpaUser user);

    List<JpaRole> getRoles();

    void deleteUser(JpaUser u);
    
}
