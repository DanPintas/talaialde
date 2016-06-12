package es.dasaur.talaialde.admin.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.dasaur.talaialde.admin.users.User;
import es.dasaur.talaialde.admin.users.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private RoleRepository roleRepo;
    
    @Override
    public List<User> findAllUsers() {
        return userRepo.findByActiveTrue();
    }

    @Override
    @Transactional(readOnly=false)
    public User saveUser(User user) {
        User storedUser = user.getId() == null ? null :
                userRepo.findOne(user.getId());
        String storedPassword = storedUser == null ? null : 
            storedUser.getPassword();
        String password = user.getPassword();
        if(storedPassword == null || !storedPassword.equals(password)){
            user.setPassword(new BCryptPasswordEncoder().encode(password));
        }
        return userRepo.save(user);
    }

    @Override
    public void deleteUser(User u) {
        u.setActive(false);
        userRepo.save(u);
    }

    @Override
    public List<Role> getRoles() {
        return roleRepo.findAll();
    }

}
