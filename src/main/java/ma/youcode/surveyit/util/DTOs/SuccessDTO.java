package ma.youcode.surveyit.util.DTOs;


public record SuccessDTO<T>(String status , String message , T data) {
}
