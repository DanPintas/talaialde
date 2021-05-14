package es.dasaur.talaialde.users;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.dasaur.talaialde.admin.users.JpaRelRoleAuthRepository;
import es.dasaur.talaialde.admin.users.JpaUser;
import es.dasaur.talaialde.admin.users.JpaUserRepository;
import es.dasaur.talaialde.users.security.LoginErrorException;

@Service
@Transactional(readOnly = true)
public class LoginServiceImpl 
        implements LoginService {
    
    @Autowired
    private transient AuthenticationManager authenticationManager;
    
    @Autowired
    private JpaUserRepository userRepo;
    
    @Autowired
    private JpaRelRoleAuthRepository relRoleAuthRepo;
    
    @Override
    public JpaUser getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public void login(String username, String password) 
            throws LoginErrorException {
        
        try {
            UsernamePasswordAuthenticationToken token = 
                    new UsernamePasswordAuthenticationToken(username, password);
    
            Authentication authentication = 
                    authenticationManager.authenticate(token);
    
            SecurityContextHolder.createEmptyContext();
            SecurityContextHolder.getContext().setAuthentication
                    (authentication);
        } catch (LoginErrorException ue) {
            throw ue;
        } 
    }

    @Override
    public List<String> getAuthsByUsername(String username) {
        JpaUser user = userRepo.findByUsername(username);
        return relRoleAuthRepo.findByRole(user.getRole()).stream()
                .map(rra -> rra.getAuth().getName())
                .collect(Collectors.toList());
    }
    
}
