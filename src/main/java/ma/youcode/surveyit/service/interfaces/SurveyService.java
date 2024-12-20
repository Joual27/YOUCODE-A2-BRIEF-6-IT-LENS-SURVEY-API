package ma.youcode.surveyit.service.interfaces;

import ma.youcode.surveyit.dto.request.survey.SurveyCreateDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyResponseDTO;
import ma.youcode.surveyit.dto.request.survey.SurveyUpdateDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyResultDTO;
import ma.youcode.surveyit.entity.Survey;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SurveyService {
    Page<SurveyResponseDTO> getAllSurveys(int page , int size);
    SurveyResponseDTO getSurvey(Long id);
    SurveyResponseDTO createSurvey(SurveyCreateDTO dto);
    SurveyResponseDTO editSurvey(SurveyUpdateDTO dto , Long id);
    void deleteSurvey(Long id);
    Survey getSurveyEntity(Long id);

    SurveyResultDTO getSurveyResults(Long id);


}
