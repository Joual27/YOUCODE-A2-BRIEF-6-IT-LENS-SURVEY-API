package ma.youcode.surveyit.exception;

import ma.youcode.surveyit.dto.response.transfer.ErrorResponseDTO;
import ma.youcode.surveyit.repository.SurveyRepository;
import ma.youcode.surveyit.util.Response;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class RestExceptionHandler {
    private  final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    public RestExceptionHandler(SurveyRepository surveyRepository) {
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception e) {

        logger.error("An unexpected error occurred: {}", e.getMessage(), e);
        return Response.error(400 , e.getMessage() , LocalDateTime.now());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException e) {
        Map<String , String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(),  fieldError.getDefaultMessage());
        });

        return Response.error(400 , "Validations errors" , LocalDateTime.now() , errors);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponseDTO handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ErrorResponseDTO(404, ex.getMessage(), LocalDateTime.now() , null);
    }
//
//    @ExceptionHandler(HandlerMethodValidationException.class)
//    public ResponseEntity<ErrorResponseDTO> HandlerMethodValidationException(HandlerMethodValidationException e) {
//
//        String responseMessage = e.getAllValidationResults().stream()
//                .map(result -> {
//                    return result.getResolvableErrors().stream()
//                            .map(error -> error.getDefaultMessage())
//                            .collect(Collectors.joining("; "));
//                })
//                .filter(message -> !message.isEmpty())
//                .collect(Collectors.joining("; "));
//
//        return Response.error(400 , responseMessage , LocalDateTime.now());
//    }
@ExceptionHandler(HandlerMethodValidationException.class)
@ResponseStatus(HttpStatus.BAD_REQUEST)
public ResponseEntity<ErrorResponseDTO> handleHandlerMethodValidationException(HandlerMethodValidationException e) {
    Map<String, String> errors = new HashMap<>();

    e.getAllValidationResults().forEach(validationResult -> {
        validationResult.getResolvableErrors().forEach(error -> {
            String message = error.getDefaultMessage();
            String field;
            if (error instanceof FieldError) {
                field = ((FieldError) error).getField();
            } else if (error instanceof ObjectError) {
                field = ((ObjectError) error).getObjectName();
            } else {
                field = "Id";
            }
            errors.put(field, message);
        });
    });

    return Response.error(HttpStatus.BAD_REQUEST.value(), "Validations errors", LocalDateTime.now(), errors );
}

}
