

package com.SpringSecurity.Service;


import com.SpringSecurity.Entity.Role;
import com.SpringSecurity.Entity.User;
import com.SpringSecurity.Repository.RoleRepository;
import com.SpringSecurity.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class CustomUserService implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Username or Email Not found"));

        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(usernameOrEmail, user.getPassword(), authorities);
    }

//    public void registerUser(String name, String email, String username, String password, Set<String> roleNames) {
//        User user = new User();
//        user.setName(name);
//        user.setEmail(email);
//        user.setUsername(username);
//        user.setPassword(passwordEncoder.encode(password));
//
//        Set<Role> roles = new HashSet<>();
//        for (String roleName : roleNames) {
//            Role role = roleRepository.findByName(roleName);
//            roles.add(role);
//        }
//        user.setRoles(roles);
//
//        userRepository.save(user);
//    }


//    public void registerUser(String name, String email, String username, String password, Set<String> roleNames) {
//        User user = new User();
//        user.setName(name);
//        user.setEmail(email);
//        user.setUsername(username);
//        user.setPassword(passwordEncoder.encode(password));
//
//        Set<Role> roles = new HashSet<>();
//        for (String roleName : roleNames) {
//            Role role = roleRepository.findByName(roleName);
//            if (role == null) {
//                throw new RuntimeException("Role not found: " + roleName);
//            }
//            roles.add(role);
//        }
//        user.setRoles(roles);
//
//        User savedUser = userRepository.save(user);
//        System.out.println("User saved with ID: " + savedUser.getId());
//    }

    private static final Logger LOGGER = Logger.getLogger(CustomUserService.class.getName());


    public void registerUser(String name, String email, String username, String password, Set<String> roleNames) {
        LOGGER.info("Registering user: " + username);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
           // Role role = roleRepository.findByName(roleName);
            Role role = roleRepository.findByName(roleName);
            if (role == null) {
                throw new RuntimeException("Role not found: " + roleName);
            }
            roles.add(role);
        }
        user.setRoles(roles);

        User savedUser = userRepository.save(user);
        LOGGER.info("User saved with ID: " + savedUser.getId());
    }
}