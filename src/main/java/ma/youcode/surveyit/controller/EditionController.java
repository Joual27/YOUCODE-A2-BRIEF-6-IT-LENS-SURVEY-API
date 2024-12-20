package ma.youcode.surveyit.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.youcode.surveyit.annotation.interfaces.Exists;
import ma.youcode.surveyit.dto.request.chapter.ChapterCreateDTO;
import ma.youcode.surveyit.dto.response.chapter.ChapterResponseDTO;
import ma.youcode.surveyit.dto.response.transfer.PageResponseDTO;
import ma.youcode.surveyit.dto.response.transfer.SuccessResponseDTO;
import ma.youcode.surveyit.dto.request.edition.EditionCreateDTO;
import ma.youcode.surveyit.dto.request.edition.EditionUpdateDTO;
import ma.youcode.surveyit.dto.response.edition.EditionResponseDTO;
import ma.youcode.surveyit.entity.Edition;
import ma.youcode.surveyit.service.interfaces.ChapterService;
import ma.youcode.surveyit.service.interfaces.EditionService;
import ma.youcode.surveyit.util.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/editions")
@AllArgsConstructor
public class EditionController {

    private final EditionService service;
    private final ChapterService chapterService;

    @GetMapping
    public ResponseEntity<SuccessResponseDTO> editions(@RequestParam(defaultValue = "0") int page , @RequestParam(value = "5") int size) {

        int index  = page > 0 ? page - 1 : 0;

        Page<EditionResponseDTO> editionsPage = service.getAllEditions(index, size);



        return Response.success(200,
                "Editions retrieve successfully",
                "editions",
                editionsPage
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> edition(
            @Valid
            @PathVariable
            @Exists(entity = Edition.class, message = "Edition Not Found") Long id
    ) {

        EditionResponseDTO response = service.getEdition(id);

        return Response.success(200,
                "Edition retrieve successfully",
                "edition",
                response
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> edit(
            @PathVariable
            @Valid @Exists(entity = Edition.class, message = "Edition Not Found") Long id,
            @RequestBody @Valid EditionUpdateDTO dto
    ) {

        EditionResponseDTO response = service.editEdition(dto, id);
        return Response.success(200, "Edition updated successfully", "edition", response);
    }

    @PostMapping
    public ResponseEntity<SuccessResponseDTO> createEdition(
            @Valid @RequestBody EditionCreateDTO dto
    ) {

        EditionResponseDTO response = service.createEdition(dto);
        return Response.success(201, "Edition Created successfully", "edition", response);
    }

    @PostMapping("/{editionId}/chapters")
    public ResponseEntity<SuccessResponseDTO> createChapter(
            @Valid @PathVariable @Exists(entity = Edition.class , message = "Edition not found.") Long editionId,@Valid @RequestBody ChapterCreateDTO dto
    ) {

        ChapterResponseDTO response = chapterService.createChapter(dto , editionId);
        return Response.success(201, "Chapter Created successfully", "chapter", response);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> delete(
            @PathVariable
            @Exists(entity = Edition.class, message = "Edition not found") Long id
    ) {
        service.deleteEdition(id);
        return Response.success(200,
                "Edition deleted successfully",
                "editionId",
                id
        );
    }
}
