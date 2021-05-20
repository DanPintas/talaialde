package es.danpintas.talaialde.users;

import es.danpintas.talaialde.admin.users.JpaUser;
import es.danpintas.talaialde.users.security.LoginErrorException;
import java.util.List;

public interface LoginService {

  JpaUser getUserByUsername(String username);

  void login(String username, String password) throws LoginErrorException;

  List<String> getAuthsByUsername(String username);

}
