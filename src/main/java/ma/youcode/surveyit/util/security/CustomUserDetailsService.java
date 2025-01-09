package ma.youcode.surveyit.util.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.youcode.magestic_cup.user.UserDAO;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

//    private final UserDAO userDAO;
//
//    @Override
//    public UserDetails loadUserByUsername(String username){
//        org.youcode.magestic_cup.user.User user = userDAO.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("No user was found with given Username"));
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
//                .collect(Collectors.toList());
//        return new User(user.getUsername() , user.getPassword() , authorities);
//    }
}