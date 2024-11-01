package org.youcode.ITLens.survey.app.ports.outbound;

import org.springframework.data.repository.query.Param;
import org.youcode.ITLens.common.interfaces.GenericDaoI;
import org.youcode.ITLens.survey.core.entity.Survey;
import org.youcode.ITLens.surveyEdition.core.entity.SurveyEdition;

import java.util.List;

public interface SurveyDAO extends GenericDaoI<Survey , Long> {
    List<SurveyEdition> findEditionsBySurveyId(@Param("surveyId") Long surveyId);
}
