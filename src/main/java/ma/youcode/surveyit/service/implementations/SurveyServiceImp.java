package ma.youcode.surveyit.service.implementations;

import jakarta.persistence.EntityExistsException;
import lombok.AllArgsConstructor;
import ma.youcode.surveyit.dto.request.survey.SurveyCreateDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyResponseDTO;
import ma.youcode.surveyit.dto.request.survey.SurveyUpdateDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyResultDTO;
import ma.youcode.surveyit.entity.*;
import ma.youcode.surveyit.exception.EntityNotFoundException;
import ma.youcode.surveyit.mapper.SurveyMapper;
import ma.youcode.surveyit.repository.SurveyRepository;
import ma.youcode.surveyit.service.interfaces.OwnerService;
import ma.youcode.surveyit.service.interfaces.SurveyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SurveyServiceImp implements SurveyService {

    private final SurveyRepository repository;
    private final SurveyMapper mapper;
    private final OwnerService ownerService;

    @Override
    @Transactional
    public SurveyResponseDTO createSurvey(SurveyCreateDTO dto) {
        Owner owner = ownerService.getOwnerEntity(dto.ownerId());

        Survey survey = mapper.toSurvey(dto);
        survey.setOwner(owner);

        return mapper.toResponseDTO(repository.save(survey));

    }

    @Override
    public SurveyResponseDTO editSurvey(SurveyUpdateDTO dto, Long id) {

        if (isTaken(dto.title(), id)) {
            throw new EntityExistsException("A survey with the title '" + dto.title() + "' already exists. Please choose a different title.");
        }

        Survey survey = mapper.toSurvey(dto);
        survey.setId(id);
        return mapper.toResponseDTO(repository.save(survey));

    }

    @Override
    public void deleteSurvey(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<SurveyResponseDTO> getAllSurveys(int page , int size) {
        Pageable pageable = PageRequest.of(page , size);
        Page<Survey> surveyPage = repository.findAll(pageable);
        return surveyPage.map(mapper::toResponseDTO);

    }

    @Override
    public SurveyResponseDTO getSurvey(Long id) {
        Survey survey = repository.findById(id).orElse(null);
        return mapper.toResponseDTO(survey);
    }

    private boolean isTaken(String title, Long id) {
        return repository.existsByTitleNotId(title, id);
    }

    @Override
    public Survey getSurveyEntity(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Survey not found."));
    }


    @Override
    public SurveyResultDTO getSurveyResults(Long id) {
        Survey survey = repository.findById(id).get(); // Custom exception
        return processSurvey(survey);
    }

    public SurveyResultDTO processSurvey(Survey survey) {

        List<SurveyResultDTO.Chapter> chapters = survey.getEditions().stream()
                .flatMap(edition -> edition.getChapters().stream()
                        .map(this::processChapter))
                .toList();

        return new SurveyResultDTO(survey.getTitle(), chapters);
    }


    private SurveyResultDTO.Chapter processChapter(Chapter chapter) {
        List<SurveyResultDTO.Subchapter> subchapters = new ArrayList<>();

        if (!chapter.getSubchapters().isEmpty()) {
            for (Chapter subchapter : chapter.getSubchapters()) {
                subchapters.add(processSubchapter(subchapter));
            }
        }

        return new SurveyResultDTO.Chapter(chapter.getTitle(), subchapters);
    }

    private SurveyResultDTO.Subchapter processSubchapter(Chapter subchapter) {
        List<SurveyResultDTO.Question> questions = new ArrayList<>();
        List<SurveyResultDTO.Subchapter> nested = new ArrayList<>();

        if (subchapter.getSubchapters().isEmpty()) {
            questions.addAll(subchapter.getQuestions().stream()
                    .map(this::processQuestion).toList());
        } else {

            for (Chapter nestedSubchapter : subchapter.getSubchapters()) {
                nested.add(processSubchapter(nestedSubchapter));
            }
        }

        return new SurveyResultDTO.Subchapter(subchapter.getTitle(), questions, nested.isEmpty() ? null : nested);

    }


    private SurveyResultDTO.Question processQuestion(Question question) {

        Map<String, Integer> answers = question.getAnswers().stream()
                .collect(Collectors.toMap(Answer::getText, Answer::getSelectionCount));

        int totalAnswers = answers.values().stream().mapToInt(Integer::intValue).sum();

        return new SurveyResultDTO.Question(question.getText(), answers, totalAnswers);
    }

}
