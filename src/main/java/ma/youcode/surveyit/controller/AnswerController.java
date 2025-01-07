package ma.youcode.surveyit.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.youcode.surveyit.annotation.interfaces.Exists;
import ma.youcode.surveyit.dto.request.answer.AnswerCreateDTO;
import ma.youcode.surveyit.dto.request.answer.AnswerUpdateDTO;
import ma.youcode.surveyit.dto.response.answer.AnswerResponseDTO;
import ma.youcode.surveyit.dto.response.transfer.PageResponseDTO;
import ma.youcode.surveyit.dto.response.transfer.SuccessResponseDTO;
import ma.youcode.surveyit.entity.Answer;
import ma.youcode.surveyit.service.interfaces.AnswerService;
import ma.youcode.surveyit.util.Response;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/answers")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class AnswerController {

    private static final Log log = LogFactory.getLog(AnswerController.class);
    private final AnswerService service;


    @GetMapping
    public ResponseEntity<SuccessResponseDTO> answers(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "5") int size) {

        int index  = page > 0 ? page - 1 : 0;

        Page<AnswerResponseDTO> answersPage = service.getAllAnswers(index , size);

        return Response.success(200, "Answers retrieve successfully", "answers", answersPage);

    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> answer(@Valid @PathVariable @Exists(entity = Answer.class, message = "Answer not found.") Long id) {

        AnswerResponseDTO response = service.getAnswer(id);
        return Response.success(200, " Answer retrieve successfully", "answer", response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> edit(
            @Valid
            @PathVariable
            @Exists(entity = Answer.class, message = "Answer not found.") Long id,
            @Valid
            @RequestBody AnswerUpdateDTO dto
    ) {

        AnswerResponseDTO response = service.editAnswer(dto, id);
        return Response.success(200,
                "Answer updated successfully",
                "answer",
                response
        );
    }

    @PostMapping
    public ResponseEntity<SuccessResponseDTO> create(
            @Valid @RequestBody AnswerCreateDTO dto
    ) {

        AnswerResponseDTO response = service.createAnswer(dto);
        return Response.success(201,
                "Answer created successfully",
                "answer",
                response
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> delete(
            @PathVariable
            @Exists(entity = Answer.class, message = "Answer not found") Long id
    ) {
        service.deleteAnswer(id);
        return Response.success(200,
                "Answer deleted successfully",
                "answerId",
                id
        );
    }


}
