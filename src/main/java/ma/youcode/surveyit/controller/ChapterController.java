package ma.youcode.surveyit.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.youcode.surveyit.annotation.interfaces.Exists;
import ma.youcode.surveyit.dto.request.chapter.ChapterCreateDTO;
import ma.youcode.surveyit.dto.request.chapter.ChapterUpdateDTO;
import ma.youcode.surveyit.dto.request.question.QuestionCreateDTO;
import ma.youcode.surveyit.dto.response.question.QuestionResponseDTO;
import ma.youcode.surveyit.dto.response.transfer.PageResponseDTO;
import ma.youcode.surveyit.dto.response.transfer.SuccessResponseDTO;
import ma.youcode.surveyit.dto.response.chapter.ChapterResponseDTO;
import ma.youcode.surveyit.entity.Chapter;
import ma.youcode.surveyit.entity.Question;
import ma.youcode.surveyit.service.interfaces.ChapterService;
import ma.youcode.surveyit.service.interfaces.QuestionService;
import ma.youcode.surveyit.util.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class ChapterController {

    private final ChapterService service;
    private final QuestionService questionService;


    @GetMapping("/chapters")
    public ResponseEntity<SuccessResponseDTO> chapters(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "5") int size) {

        int index  = page > 0 ? page - 1 : 0;

        Page<ChapterResponseDTO> chaptersPage = service.getAllChapters(index, size);

        return Response.success(200,
                "Chapters retrieve successfully",
                "chapters",
                chaptersPage        );

    }

    @GetMapping("/chapters/{id}")
    public ResponseEntity<SuccessResponseDTO> chapter(
            @Valid
            @PathVariable
            @Exists(entity = Chapter.class, message = "Chapter Not Found") Long id
    ) {

        ChapterResponseDTO response = service.getChapter(id);

        return Response.success(200,
                " Chapter retrieve successfully",
                "chapter",
                response
        );
    }

//    Update chapter
    @PutMapping("/chapters/{id}")
    public ResponseEntity<SuccessResponseDTO> editChapter(
            @Valid
            @PathVariable
            @Exists(entity = Chapter.class, message = "Chapter Not Found") Long id,
            @Valid
            @RequestBody ChapterUpdateDTO dto
    ) {

        ChapterResponseDTO response = service.editChapter(dto, id);
        return Response.success(200,
                "Chapter updated successfully",
                "chapter",
                response
        );
    }

    //    Update subchapter
    @PutMapping("/subchapters/{id}")
    public ResponseEntity<SuccessResponseDTO> editSubChapter(
            @Valid
            @PathVariable
            @Exists(entity = Chapter.class, message = "Subchapter Not Found") Long id,
            @Valid
            @RequestBody ChapterUpdateDTO dto
    ) {

        ChapterResponseDTO response = service.editChapter(dto, id);
        return Response.success(200,
                "Subchapter updated successfully",
                "chapter",
                response
        );
    }

    // Add subchapter
    @PostMapping("chapters/{chapterId}/subchapters")
    public ResponseEntity<SuccessResponseDTO> createSubchapter(
            @Valid @RequestBody ChapterCreateDTO dto , @Valid @PathVariable @Exists(entity = Chapter.class , message = "Chapter not found.") Long chapterId
    ) {
        ChapterResponseDTO response = service.createChapter(dto , chapterId);
        return Response.success(201,
                "Subchapter created successfully",
                "subchapter",
                response
        );
    }


    @PostMapping("subchapters/{subchapterId}/questions")
    public ResponseEntity<SuccessResponseDTO> createQuestion(
            @Valid @RequestBody QuestionCreateDTO dto , @Valid @PathVariable @Exists(entity = Chapter.class , message = "Subchapter not found.") Long subchapterId
    ) {
        QuestionResponseDTO response = questionService.createQuestion(dto , subchapterId);
        return Response.success(201,
                "Question created successfully",
                "question",
                response
        );
    }

    @DeleteMapping("/chapters/{id}")
    public ResponseEntity<SuccessResponseDTO> deleteChapter(
            @PathVariable
            @Exists(entity = Chapter.class, message = "Chapter not found") Long id
    ) {
        service.deleteChapter(id);
        return Response.success(200,
                "Chapter deleted successfully",
                "chapterId",
                id
        );
    }

    @DeleteMapping("/subchapters/{id}")
    public ResponseEntity<SuccessResponseDTO> deleteSubChapter(
            @PathVariable
            @Exists(entity = Chapter.class, message = "Subchapter not found") Long id
    ) {
        service.deleteChapter(id);
        return Response.success(200,
                "Chapter deleted successfully",
                "subchapterId",
                id
        );
    }
}
