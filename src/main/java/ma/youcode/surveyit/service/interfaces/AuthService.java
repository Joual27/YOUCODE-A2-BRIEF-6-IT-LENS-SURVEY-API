package ma.youcode.surveyit.service.interfaces;

import ma.youcode.surveyit.dto.request.auth.LoginDTO;
import ma.youcode.surveyit.dto.request.auth.OwnerRegistrationDTO;
import ma.youcode.surveyit.dto.response.auth.AuthResponseDTO;

public interface AuthService {
    AuthResponseDTO ownerRegistration(OwnerRegistrationDTO credentials);
    AuthResponseDTO authenticate(LoginDTO credentials);
}
