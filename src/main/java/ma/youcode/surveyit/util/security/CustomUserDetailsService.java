package ma.youcode.surveyit.util.security;

import lombok.AllArgsConstructor;
import ma.youcode.surveyit.entity.User;
import ma.youcode.surveyit.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

     private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user was found with given Username"));
        List<GrantedAuthority> authorities = user.getAuthorities().stream().toList();
        return new org.springframework.security.core.userdetails.User(user.getUsername() , user.getPassword() , authorities);
    }
}