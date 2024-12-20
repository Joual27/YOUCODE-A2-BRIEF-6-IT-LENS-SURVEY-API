package ma.youcode.surveyit.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.youcode.surveyit.annotation.interfaces.Exists;
import ma.youcode.surveyit.dto.request.question.QuestionUpdateDTO;
import ma.youcode.surveyit.dto.response.answer.AnswerResponseDTO;
import ma.youcode.surveyit.dto.response.question.QuestionResponseDTO;
import ma.youcode.surveyit.dto.response.transfer.PageResponseDTO;
import ma.youcode.surveyit.dto.response.transfer.SuccessResponseDTO;
import ma.youcode.surveyit.entity.Question;
import ma.youcode.surveyit.service.interfaces.QuestionService;
import ma.youcode.surveyit.util.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@AllArgsConstructor
public class QuestionController {

    private final QuestionService service;

    @GetMapping
    public ResponseEntity<SuccessResponseDTO> questions(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "5") int size) {

        int index  = page > 0 ? page - 1 : 0;

        Page<QuestionResponseDTO> questionsPage = service.getAllQuestions(index , size);

        return Response.success(200,
                "Questions retrieve successfully",
                "questions",
                questionsPage
        );


    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> question(
            @Valid
            @PathVariable
            @Exists(entity = Question.class, message = "Question not found.") Long id
    ) {

        QuestionResponseDTO response = service.getQuestion(id);

        return Response.success(200,
                "Question retrieve successfully",
                "question",
                response
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> edit(
            @PathVariable
            @Valid @Exists(entity = Question.class, message = "Question not found.") Long id,
            @RequestBody @Valid QuestionUpdateDTO dto
    ) {

        QuestionResponseDTO response = service.editQuestion(dto, id);
        return Response.success(200, "Question updated successfully", "question", response);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> delete(
            @PathVariable
            @Exists(entity = Question.class, message = "Question not found.") Long id
    ) {
        service.deleteQuestion(id);
        return Response.success(200,
                "Question deleted successfully",
                "questionId",
                id
        );
    }
}
