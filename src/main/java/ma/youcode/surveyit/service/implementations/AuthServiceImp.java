package ma.youcode.surveyit.service.implementations;

import lombok.AllArgsConstructor;
import ma.youcode.surveyit.dto.request.auth.OwnerRegistrationDTO;
import ma.youcode.surveyit.dto.response.auth.AuthResponseDTO;
import ma.youcode.surveyit.entity.Owner;
import ma.youcode.surveyit.mapper.AuthMapper;
import ma.youcode.surveyit.repository.OwnerRepository;
import ma.youcode.surveyit.service.interfaces.AuthService;
import ma.youcode.surveyit.util.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthServiceImp implements AuthService {
    private final OwnerRepository ownerRepository;
    private final JwtService jwtService;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO ownerRegistration(OwnerRegistrationDTO data) {
        Owner ownerToCreate = authMapper.toOwner(data);
        ownerToCreate.setPassword(passwordEncoder.encode(data.password()));
        Owner createdOwner = ownerRepository.save(ownerToCreate);
        String token = jwtService.generateToken(createdOwner);
        return new AuthResponseDTO(createdOwner.getId() , token , jwtService.extractRole(token).get(0));
    }
}
