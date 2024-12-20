package ma.youcode.surveyit.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.youcode.surveyit.dto.response.transfer.PageResponseDTO;
import ma.youcode.surveyit.dto.response.transfer.SuccessResponseDTO;
import ma.youcode.surveyit.dto.request.owner.OwnerCreateDTO;
import ma.youcode.surveyit.dto.response.owner.OwnerResponseDTO;
import ma.youcode.surveyit.dto.request.owner.OwnerUpdateDTO;
import ma.youcode.surveyit.entity.Owner;
import ma.youcode.surveyit.service.interfaces.OwnerService;
import ma.youcode.surveyit.util.Response;
import ma.youcode.surveyit.annotation.interfaces.Exists;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService service;

    @GetMapping
    public ResponseEntity<SuccessResponseDTO> owners(@RequestParam(defaultValue = "0") int page , @RequestParam(defaultValue = "5") int size) {

        int index  = page > 0 ? page - 1 : 0;
        Page<OwnerResponseDTO> ownersPage = service.getAllOwners(index , size);

        return Response.success(200,
                "Owners retrieve successfully",
                "owners",
                ownersPage
        );

    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> owner(
            @Valid
            @PathVariable
            @Exists(entity = Owner.class, message = "Owner Not Found") Long id
    ) {

        OwnerResponseDTO response = service.getOwner(id);
        System.out.println(response.surveys().toString());
        return Response.success(200,
                " Owner retrieve successfully",
                "owner",
                response
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> edit(
            @PathVariable
            @Valid @Exists(entity = Owner.class, message = "Owner Not Found") Long id,
            @RequestBody @Valid OwnerUpdateDTO dto
    ) {

        OwnerResponseDTO response = service.editOwner(dto, id);
        return Response.success(200,
                "Owner updated successfully",
                "owner",
                response
        );
    }

    @PostMapping
    public ResponseEntity<SuccessResponseDTO> create(
            @Valid @RequestBody OwnerCreateDTO dto
    ) {

        OwnerResponseDTO response = service.createOwner(dto);
        return Response.success(201,
                "Owner Created successfully",
                "owner",
                response
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponseDTO> delete(
            @PathVariable
            @Exists(entity = Owner.class, message = "Owner not found") Long id
    ) {
        service.deleteOwner(id);
        return Response.success(200,
                "Owner deleted successfully",
                "ownerId",
                id
        );
    }
}
