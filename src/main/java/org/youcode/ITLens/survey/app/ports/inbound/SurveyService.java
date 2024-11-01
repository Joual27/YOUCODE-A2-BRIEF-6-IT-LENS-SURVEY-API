package org.youcode.ITLens.survey.app.ports.inbound;

import org.youcode.ITLens.survey.core.entity.DTOs.CreateSurveyDTO;
import org.youcode.ITLens.survey.core.entity.DTOs.SurveyResponseDTO;

public interface SurveyService {
    SurveyResponseDTO save(CreateSurveyDTO surveyData);
}
