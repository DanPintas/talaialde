package es.dasaur.talaialde.users;

import java.util.List;

import es.dasaur.talaialde.admin.users.JpaUser;
import es.dasaur.talaialde.users.security.LoginErrorException;

public interface LoginService {

    JpaUser getUserByUsername(String username);

    void login(String username, String password) throws LoginErrorException;

    List<String> getAuthsByUsername(String username);

}
