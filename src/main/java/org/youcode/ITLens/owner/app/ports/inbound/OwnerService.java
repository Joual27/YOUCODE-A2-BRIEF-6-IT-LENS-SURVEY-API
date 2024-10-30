package org.youcode.ITLens.owner.app.ports.inbound;

import org.youcode.ITLens.owner.core.entities.DTOs.OwnerResponseDTO;

public interface OwnerService {
    OwnerResponseDTO getOwnerById(Long id);
}
