package ma.youcode.surveyit.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.youcode.surveyit.dto.request.auth.LoginDTO;
import ma.youcode.surveyit.dto.request.auth.OwnerRegistrationDTO;
import ma.youcode.surveyit.dto.response.auth.AuthResponseDTO;
import ma.youcode.surveyit.service.interfaces.AuthService;
import ma.youcode.surveyit.util.DTOs.SuccessDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/public")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/owner")
    public ResponseEntity<SuccessDTO<AuthResponseDTO>> registerAsOwner(@RequestBody @Valid OwnerRegistrationDTO req){
        AuthResponseDTO res = authService.ownerRegistration(req);
        return new ResponseEntity<>(new SuccessDTO<>("success" , "owner registred successfully !" , res) , HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessDTO<AuthResponseDTO>> login(@RequestBody @Valid LoginDTO req){
        AuthResponseDTO res = authService.authenticate(req);
        return new ResponseEntity<>(new SuccessDTO<>("success" , "Signed in successfully !" , res) , HttpStatus.OK);
    }
}
