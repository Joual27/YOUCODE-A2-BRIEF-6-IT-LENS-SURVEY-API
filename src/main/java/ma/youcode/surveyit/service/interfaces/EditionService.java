package ma.youcode.surveyit.service.interfaces;

import ma.youcode.surveyit.dto.request.edition.EditionCreateDTO;
import ma.youcode.surveyit.dto.request.edition.EditionUpdateDTO;
import ma.youcode.surveyit.dto.response.edition.EditionResponseDTO;
import ma.youcode.surveyit.entity.Edition;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EditionService {
    Page<EditionResponseDTO> getAllEditions(int page , int size);
    EditionResponseDTO getEdition(Long id);
    EditionResponseDTO createEdition(EditionCreateDTO dto);
    EditionResponseDTO editEdition(EditionUpdateDTO dto , Long id);
    void deleteEdition(Long id);
    Edition getEditionEntity(Long id);



}
