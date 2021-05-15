package es.danpintas.talaialde.admin.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    
    @Autowired
    private JpaUserRepository userRepo;
    
    @Autowired
    private JpaRoleRepository roleRepo;
    
    @Override
    public List<JpaUser> findAllUsers() {
        return userRepo.findByActiveTrue();
    }

    @Override
    @Transactional(readOnly=false)
    public JpaUser saveUser(JpaUser user) {
        JpaUser storedUser = Optional.ofNullable(user.getId())
                .flatMap(userRepo::findById)
                .orElse(null);
        String storedPassword = storedUser == null ? null :
            storedUser.getPassword();
        String password = user.getPassword();
        if(storedPassword == null || !storedPassword.equals(password)){
            user.setPassword(new BCryptPasswordEncoder().encode(password));
        }
        return userRepo.save(user);
    }

    @Override
    public void deleteUser(JpaUser u) {
        u.setActive(false);
        userRepo.save(u);
    }

    @Override
    public List<JpaRole> getRoles() {
        return roleRepo.findAll();
    }

}
