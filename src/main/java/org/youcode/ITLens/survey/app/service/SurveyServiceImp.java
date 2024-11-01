package org.youcode.ITLens.survey.app.service;

import org.springframework.stereotype.Service;
import org.youcode.ITLens.owner.app.ports.inbound.OwnerService;
import org.youcode.ITLens.survey.app.ports.inbound.SurveyService;
import org.youcode.ITLens.survey.app.ports.outbound.SurveyDAO;
import org.youcode.ITLens.survey.core.entity.DTOs.CreateSurveyDTO;
import org.youcode.ITLens.survey.core.entity.DTOs.SurveyResponseDTO;
import org.youcode.ITLens.survey.core.entity.Survey;
import org.youcode.ITLens.survey.core.entity.mappers.CreateSurveyDTOToSurveyEntityMapper;
import org.youcode.ITLens.survey.core.entity.mappers.SurveyEntityToSurveyResponseDTOMapper;
import org.youcode.ITLens.survey.infra.adapters.outbound.persistence.SurveyPersistenceAdapter;
import org.youcode.ITLens.surveyEdition.core.entity.SurveyEdition;

import java.util.List;

@Service
public class SurveyServiceImp implements SurveyService {
    private final OwnerService ownerService;
    private final SurveyPersistenceAdapter surveyPersistenceAdapter ;
    private final CreateSurveyDTOToSurveyEntityMapper createSurveyDTOToSurveyEntityMapper;
    private final SurveyEntityToSurveyResponseDTOMapper surveyEntityToSurveyResponseDTOMapper;

    public SurveyServiceImp(SurveyPersistenceAdapter surveyPersistenceAdapter , CreateSurveyDTOToSurveyEntityMapper createSurveyDTOToSurveyEntityMapper , SurveyEntityToSurveyResponseDTOMapper surveyEntityToSurveyResponseDTOMapper , OwnerService ownerService){
        this.surveyPersistenceAdapter = surveyPersistenceAdapter;
        this.createSurveyDTOToSurveyEntityMapper = createSurveyDTOToSurveyEntityMapper;
        this.surveyEntityToSurveyResponseDTOMapper = surveyEntityToSurveyResponseDTOMapper;
        this.ownerService = ownerService;
    }

    @Override
    public SurveyResponseDTO save(CreateSurveyDTO surveyData){
        Survey surveyToCreate = createSurveyDTOToSurveyEntityMapper.toEntity(surveyData);
        Survey createdSurvey = surveyPersistenceAdapter.save(surveyToCreate);
        createdSurvey.setOwner(ownerService.getOwnerEntityById(surveyData.ownerId()));
        createdSurvey.setEditions(getEditionsOfSurvey(createdSurvey.getId()));
        return surveyEntityToSurveyResponseDTOMapper.entityToDto(createdSurvey);
    }

    @Override
    public List<SurveyEdition> getEditionsOfSurvey(Long id){
        return surveyPersistenceAdapter.findEditionsBySurveyId(id);
    }

}
