package ma.youcode.surveyit.dto.request.auth;

import jakarta.validation.constraints.NotNull;

public record LoginDTO (@NotNull String username ,@NotNull String password){
}
