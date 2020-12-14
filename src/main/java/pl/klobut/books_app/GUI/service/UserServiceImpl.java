package pl.klobut.books_app.GUI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.klobut.books_app.GUI.db.RoleRepo;
import pl.klobut.books_app.GUI.db.UserRepo;
import pl.klobut.books_app.GUI.entity.User;

import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepo userRepository;
    @Autowired
    private RoleRepo roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

//    @Bean
//    BCryptPasswordEncoder passwordEncoder()
//    {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);

        System.out.println(passwordEncoder.encode("1234"));
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByLogin(username);
    }
}
