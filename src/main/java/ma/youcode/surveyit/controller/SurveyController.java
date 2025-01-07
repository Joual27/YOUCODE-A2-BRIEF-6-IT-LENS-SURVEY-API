package ma.youcode.surveyit.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.youcode.surveyit.dto.response.survey.SurveyResultDTO;
import ma.youcode.surveyit.dto.response.transfer.PageResponseDTO;
import ma.youcode.surveyit.dto.response.transfer.SuccessResponseDTO;
import ma.youcode.surveyit.dto.request.survey.SurveyCreateDTO;
import ma.youcode.surveyit.dto.request.survey.SurveyUpdateDTO;
import ma.youcode.surveyit.dto.response.survey.SurveyResponseDTO;
import ma.youcode.surveyit.entity.Survey;
import ma.youcode.surveyit.service.interfaces.ParticipateService;
import ma.youcode.surveyit.service.interfaces.SurveyService;
import ma.youcode.surveyit.util.Converter;
import ma.youcode.surveyit.util.Response;
import ma.youcode.surveyit.annotation.interfaces.Exists;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/surveys")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class SurveyController {

    private static final Log log = LogFactory.getLog(SurveyController.class);
    private final SurveyService service;
    private final ParticipateService participateService;

    @GetMapping
    public ResponseEntity<SuccessResponseDTO> surveys(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "10") int size) {

        int index  = page > 0 ? page - 1 : 0;
        Page<SurveyResponseDTO> pageSurvey = service.getAllSurveys(index , size);

        return Response.success(200,
                "Surveys retrieve successfully",
                "surveys" ,
                pageSurvey
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> survey(
            @Valid
            @PathVariable
            @Exists(entity = Survey.class, message = "Survey Not Found") Long id
    ) {

        SurveyResponseDTO response = service.getSurvey(id);

        return Response.success(200,
                " Survey retrieve successfully",
                "survey",
                response
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> edit(
            @Valid
            @PathVariable
            @Exists(entity = Survey.class, message = "Survey not found.") Long id,
            @Valid
            @RequestBody SurveyUpdateDTO dto
    ) {

        SurveyResponseDTO response = service.editSurvey(dto, id);
        return Response.success(200,
                "Survey updated successfully",
                "survey",
                response
        );
    }

    @PostMapping
    public ResponseEntity<SuccessResponseDTO> create(
            @Valid @RequestBody SurveyCreateDTO dto
    ) {

        SurveyResponseDTO response = service.createSurvey(dto);
        return Response.success(201,
                "Survey Created successfully",
                "survey",
                response
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> delete(
            @PathVariable
            @Exists(entity = Survey.class, message = "Survey not found") Long id
    ) {
        service.deleteSurvey(id);
        return Response.success(200,
                "Survey deleted successfully",
                "surveyId",
                id
        );
    }

    @PostMapping("{surveyId}/participate")
    public ResponseEntity<SuccessResponseDTO> participate(@RequestBody String request , @PathVariable Long surveyId){
        Object dto =  Converter.process(request);
        participateService.participateProcess(dto);

        return Response.success(201 , "Participated recorded successfully");
    }

    @GetMapping("{surveyId}/results")
    public ResponseEntity<SuccessResponseDTO> results( @PathVariable @Exists(entity = Survey.class, message = "Survey not found") Long surveyId){

        SurveyResultDTO results = service.getSurveyResults(surveyId);
        return Response.success(200 , "Results for survey successfully" , "results",results);
    }

}
