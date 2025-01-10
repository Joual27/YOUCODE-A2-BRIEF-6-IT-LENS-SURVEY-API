package ma.youcode.surveyit.controller;

import lombok.AllArgsConstructor;
import ma.youcode.surveyit.service.interfaces.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/public")
public class AuthController {

    private final AuthService authService;

//    @PostMapping("/register/owner")
//    public ResponseEntity<>
}
