package za.co.bakery.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import za.co.bakery.backend.repository.UserRepository;

import java.util.Collections;

public class UserDetailsServiceImplementation implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public UserDetailsServiceImplementation(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        za.co.bakery.backend.data.entity.User user = userRepository.findByEmailIgnoreCase(username);
        if(null == user){
            throw new UsernameNotFoundException("No user found with that email.");
        }else {
            return new User(user.getEmail(), user.getHashedPassword(), Collections.singletonList(new SimpleGrantedAuthority("ROlE_"+user.getRole())));
        }
    }
}
