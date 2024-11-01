package org.youcode.ITLens.survey.app.ports.inbound;

import org.youcode.ITLens.survey.core.entity.DTOs.CreateSurveyDTO;
import org.youcode.ITLens.survey.core.entity.DTOs.SurveyResponseDTO;
import org.youcode.ITLens.survey.core.entity.Survey;
import org.youcode.ITLens.surveyEdition.core.entity.SurveyEdition;

import java.util.List;

public interface SurveyService {
    SurveyResponseDTO save(CreateSurveyDTO surveyData);
    List<SurveyEdition> getEditionsOfSurvey(Long id);
}
