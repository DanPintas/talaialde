package es.dasaur.talaialde.admin.users;

import java.util.List;

import es.dasaur.talaialde.admin.users.User;

public interface UserService {

    List<User> findAllUsers();
    
    User saveUser(User user);

    List<Role> getRoles();

    void deleteUser(User u);
    
}
