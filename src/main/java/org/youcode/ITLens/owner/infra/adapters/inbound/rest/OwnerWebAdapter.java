package org.youcode.ITLens.owner.infra.adapters.inbound.rest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.youcode.ITLens.owner.app.ports.inbound.OwnerService;
import org.youcode.ITLens.owner.core.entities.DTOs.OwnerResponseDTO;

@RestController
@RequestMapping("/api/v1/owners")
public class OwnerWebAdapter {
    private final OwnerService ownerService;

    public OwnerWebAdapter(OwnerService ownerService){
        this.ownerService = ownerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OwnerResponseDTO> getOwnerBydId(@PathVariable("id") Long id){
       OwnerResponseDTO res = ownerService.getOwnerById(id);
       return new ResponseEntity<>(res , HttpStatus.OK);
    }

}
