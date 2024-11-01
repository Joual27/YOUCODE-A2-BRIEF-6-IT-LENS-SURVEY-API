package org.youcode.ITLens.survey.infra.adapters.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.youcode.ITLens.survey.app.ports.outbound.SurveyDAO;
import org.youcode.ITLens.survey.core.entity.Survey;

public interface SurveyPersistenceAdapter extends SurveyDAO , JpaRepository<Survey , Long> {
}
