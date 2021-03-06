package es.danpintas.talaialde.users.security;

import es.danpintas.talaialde.admin.users.JpaUser;
import es.danpintas.talaialde.users.LoginService;
import es.danpintas.talaialde.users.security.LoginErrorException.LoginErrorType;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DbAuthenticationProvider implements AuthenticationProvider {

  @Autowired
  private LoginService loginService;

  @Override
  public Authentication authenticate(Authentication authentication)
      throws AuthenticationException {

    UsernamePasswordAuthenticationToken token = null;
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();
    List<GrantedAuthority> grantedAuths = new ArrayList<>();

    JpaUser user = loginService.getUserByUsername(username);

    if (user != null) {
      String encodedPassword = user.getPassword();
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      if (passwordEncoder.matches(password, encodedPassword)) {
        loginService.getAuthsByUsername(username).forEach(
            auth -> grantedAuths.add(new SimpleGrantedAuthority(auth)));
        token = new UsernamePasswordAuthenticationToken(
            username, password, grantedAuths);
      } else {
        throw new LoginErrorException(LoginErrorType.WRONG_PASSWORD);
      }
    } else {
      throw new LoginErrorException(LoginErrorType.NO_USER);
    }

    return token;
  }

  @Override
  public boolean supports(Class<?> auth) {
    return true;
  }

}
