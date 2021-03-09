package reactspring.task_manager.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactspring.task_manager.entities.Users;
import reactspring.task_manager.repositories.UserRepository;
import reactspring.task_manager.services.UserService;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(s);

        if(user!=null){
            return user;
        }
        else {
            throw new UsernameNotFoundException("USER NOT FOUND");
        }
    }
}
