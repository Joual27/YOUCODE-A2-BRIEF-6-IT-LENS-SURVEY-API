package org.youcode.ITLens.survey.infra.adapters.inbound.rest;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youcode.ITLens.survey.app.ports.inbound.SurveyService;
import org.youcode.ITLens.survey.core.entity.DTOs.CreateSurveyDTO;
import org.youcode.ITLens.survey.core.entity.DTOs.SurveyResponseDTO;
import org.youcode.ITLens.utils.DTOs.SuccessDTO;

@RestController
@RequestMapping("api/v1/surveys")
public class SurveyWebAdapter {

    private final SurveyService surveyService;

    public SurveyWebAdapter(SurveyService surveyService){
        this.surveyService = surveyService;
    }

    @PostMapping("/create")
    public ResponseEntity<SuccessDTO<SurveyResponseDTO>> createSurvey(@Valid @RequestBody CreateSurveyDTO req){
        SurveyResponseDTO res = surveyService.save(req);
        return new ResponseEntity<>(new SuccessDTO<SurveyResponseDTO>("success" , "Survey created successFully" , res) , HttpStatus.OK);
    }

}
