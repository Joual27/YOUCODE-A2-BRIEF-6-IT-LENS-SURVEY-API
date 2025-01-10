package ma.youcode.surveyit.service.implementations;

import lombok.AllArgsConstructor;
import ma.youcode.surveyit.dto.request.auth.LoginDTO;
import ma.youcode.surveyit.dto.request.auth.OwnerRegistrationDTO;
import ma.youcode.surveyit.dto.response.auth.AuthResponseDTO;
import ma.youcode.surveyit.entity.Owner;
import ma.youcode.surveyit.entity.User;
import ma.youcode.surveyit.exception.EntityNotFoundException;
import ma.youcode.surveyit.mapper.AuthMapper;
import ma.youcode.surveyit.repository.OwnerRepository;
import ma.youcode.surveyit.repository.UserRepository;
import ma.youcode.surveyit.service.interfaces.AuthService;
import ma.youcode.surveyit.util.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthServiceImp implements AuthService {
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDTO ownerRegistration(OwnerRegistrationDTO credentials) {
        Owner ownerToCreate = authMapper.toOwner(credentials);
        ownerToCreate.setPassword(passwordEncoder.encode(credentials.password()));
        Owner createdOwner = ownerRepository.save(ownerToCreate);
        String token = jwtService.generateToken(createdOwner);
        Authentication auth = new UsernamePasswordAuthenticationToken(createdOwner , null , createdOwner.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
        return new AuthResponseDTO(createdOwner.getId() , token , jwtService.extractRole(token).get(0));
    }

    @Override
    public AuthResponseDTO authenticate(LoginDTO credentials){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credentials.username() , credentials.password());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User loggedUser = getUserEntityByUsername(userDetails.getUsername());
        return new AuthResponseDTO(loggedUser.getId(), token , jwtService.extractRole(token).get(0));
    }

    private User getUserEntityByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("No user found with given Id"));
    }

}
