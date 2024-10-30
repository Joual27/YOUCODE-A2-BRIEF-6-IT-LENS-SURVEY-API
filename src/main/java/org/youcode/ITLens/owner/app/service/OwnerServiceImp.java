package org.youcode.ITLens.owner.app.service;

import org.springframework.stereotype.Service;
import org.youcode.ITLens.owner.app.ports.inbound.OwnerService;
import org.youcode.ITLens.owner.core.entities.DTOs.OwnerResponseDTO;
import org.youcode.ITLens.owner.core.entities.Owner;
import org.youcode.ITLens.owner.core.entities.mappers.OwnerEntityToOwnerResponseDTOMapper;
import org.youcode.ITLens.owner.infra.adapters.outbound.persistence.OwnerPersistenceAdapter;

@Service
public class OwnerServiceImp implements OwnerService {

    private final OwnerPersistenceAdapter ownerPersistenceAdapter;
    private final OwnerEntityToOwnerResponseDTOMapper ownerEntityToOwnerResponseDTOMapper;

    public OwnerServiceImp(OwnerPersistenceAdapter ownerPersistenceAdapter , OwnerEntityToOwnerResponseDTOMapper ownerEntityToOwnerResponseDTOMapper){
        this.ownerPersistenceAdapter = ownerPersistenceAdapter;
        this.ownerEntityToOwnerResponseDTOMapper = ownerEntityToOwnerResponseDTOMapper;
    }

    @Override
    public OwnerResponseDTO getOwnerById(Long id){
        Owner o = null;
        if (ownerPersistenceAdapter.findById(id).isPresent()){
            o = ownerPersistenceAdapter.findById(id).get();
            System.out.println(o.getName());
            return ownerEntityToOwnerResponseDTOMapper.entityToDto(o);
        }
        return null;
    }
}
