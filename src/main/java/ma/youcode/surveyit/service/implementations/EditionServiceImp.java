package ma.youcode.surveyit.service.implementations;

import lombok.AllArgsConstructor;
import ma.youcode.surveyit.dto.request.edition.EditionCreateDTO;
import ma.youcode.surveyit.dto.request.edition.EditionUpdateDTO;
import ma.youcode.surveyit.dto.response.edition.EditionResponseDTO;
import ma.youcode.surveyit.entity.Edition;
import ma.youcode.surveyit.entity.Survey;
import ma.youcode.surveyit.exception.EntityNotFoundException;
import ma.youcode.surveyit.mapper.EditionMapper;
import ma.youcode.surveyit.repository.EditionRepository;
import ma.youcode.surveyit.service.interfaces.EditionService;
import ma.youcode.surveyit.service.interfaces.SurveyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EditionServiceImp implements EditionService {

    private final EditionRepository repository;
    private final EditionMapper mapper;
    private final SurveyService surveyService;

    @Override
    @Transactional
    public EditionResponseDTO createEdition(EditionCreateDTO dto) {

        Survey survey = surveyService.getSurveyEntity(dto.surveyId());

        Edition edition = mapper.toEdition(dto);
        edition.setSurvey(survey);

        return mapper.toResponseDTO(repository.save(edition));

    }

    @Override
    public EditionResponseDTO editEdition(EditionUpdateDTO dto, Long id) {

        Edition edition = mapper.toEdition(dto);
        edition.setId(id);
        return mapper.toResponseDTO(repository.save(edition));

    }

    @Override
    public void deleteEdition(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<EditionResponseDTO> getAllEditions(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Edition> editionPage = repository.findAll(pageable);

        return editionPage.map(mapper::toResponseDTO);

    }

    @Override
    public EditionResponseDTO getEdition(Long id) {
        Edition edition = repository.findById(id).orElse(null);
        return mapper.toResponseDTO(edition);
    }

    @Override
    public Edition getEditionEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Edition not found."));
    }
}
