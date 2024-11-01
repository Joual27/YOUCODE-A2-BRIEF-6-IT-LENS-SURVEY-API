package org.youcode.ITLens.survey.infra.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.youcode.ITLens.survey.app.ports.outbound.SurveyDAO;
import org.youcode.ITLens.survey.core.entity.Survey;
import org.youcode.ITLens.surveyEdition.core.entity.SurveyEdition;

import java.util.List;


public interface SurveyPersistenceAdapter extends SurveyDAO , JpaRepository<Survey , Long> {
    @Query("SELECT e FROM SurveyEdition e WHERE e.survey.id = :surveyId")
    List<SurveyEdition> findEditionsBySurveyId(@Param("surveyId") Long surveyId);
}
