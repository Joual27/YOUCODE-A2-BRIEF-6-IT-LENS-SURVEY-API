package ma.youcode.surveyit.dto.response.transfer;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SuccessResponseDTO(int status , String message , Map<String , Object> data , PageResponseDTO pageInfo, LocalDateTime timestamp) {
    public SuccessResponseDTO(int status , String message , Map<String , Object> data , LocalDateTime timestamp) {
        this(status , message , data , null , timestamp);
    }
}
