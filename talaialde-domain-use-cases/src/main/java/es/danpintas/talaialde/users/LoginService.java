package es.danpintas.talaialde.users;

import es.danpintas.talaialde.users.security.LoginErrorException;
import java.util.List;

import es.danpintas.talaialde.admin.users.JpaUser;

public interface LoginService {

    JpaUser getUserByUsername(String username);

    void login(String username, String password) throws LoginErrorException;

    List<String> getAuthsByUsername(String username);

}
