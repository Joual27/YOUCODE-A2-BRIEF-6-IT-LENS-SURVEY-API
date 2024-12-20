package ma.youcode.surveyit.dto.response.transfer;

public record PageResponseDTO(Long totalElements ,int totalPages , int size , int current , boolean hasPrevious , boolean hasNext) {
}
