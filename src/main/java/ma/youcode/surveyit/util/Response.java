package ma.youcode.surveyit.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import ma.youcode.surveyit.dto.response.transfer.PageResponseDTO;
import ma.youcode.surveyit.dto.response.transfer.SuccessResponseDTO;
import ma.youcode.surveyit.dto.response.transfer.ErrorResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class Response {

    public static ResponseEntity<SuccessResponseDTO> success(int status, String message, String key,  Page<?> items) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(key, items.getContent());

        PageResponseDTO pageDTO = new PageResponseDTO(
                items.getTotalElements(),
                items.getTotalPages(),
                items.getSize(),
                items.getNumber() + 1,
                items.hasPrevious(),
                items.hasNext()
        );
        return ResponseEntity.status(status).body((new SuccessResponseDTO(status, message, payload, pageDTO ,LocalDateTime.now())));
    }

    public static ResponseEntity<SuccessResponseDTO> success(int status, String message, String key, Object value) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(key, value);
        return ResponseEntity.status(status).body((new SuccessResponseDTO(status, message, payload ,LocalDateTime.now())));
    }
    public static ResponseEntity<SuccessResponseDTO> success(int status, String message) {
        return ResponseEntity.status(status).body((new SuccessResponseDTO(status, message, null, null,LocalDateTime.now())));
    }

    public static ResponseEntity<ErrorResponseDTO> error(int status, String message, LocalDateTime timestamp) {
        return ResponseEntity.status(status).body((new ErrorResponseDTO(status, message, timestamp , null)));
    }

    public static ResponseEntity<ErrorResponseDTO> error(int status, String message, LocalDateTime timestamp, Map<String, String> errors) {
        return ResponseEntity.status(status).body((new ErrorResponseDTO(status, message, timestamp, errors)));
    }


}
